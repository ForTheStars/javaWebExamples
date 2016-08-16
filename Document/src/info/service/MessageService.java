package info.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.URLDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.NEW;

import info.dao.IAttachmentDao;
import info.dao.IMessageDao;
import info.dao.IUserDao;
import info.dto.AttachDto;
import info.model.Attachment;
import info.model.Message;
import info.model.Pager;
import info.model.SystemContext;
import info.model.User;
import info.model.UserEmail;
import info.model.UserMessage;
import info.util.DocumentUtil;

@Service("messageService")
public class MessageService implements IMessageService {
	private IMessageDao messageDao;
	private IUserDao userDao;
	private IAttachmentDao attachmentDao;
	private JavaMailSender mailSender;
	private TaskExecutor taskExecutor;
	
	public IMessageDao getMessageDao() {
		return messageDao;
	}
	@Resource
	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Resource
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	@Resource
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	@Override
	public void add(Message msg, Integer[] userIds, AttachDto attachDto, int isEmail) throws IOException {
		msg.setUser(SystemContext.getLoginUser());
		msg.setCreateDate(new Date());
		messageDao.add(msg);
		UserMessage uMessage = null;
		List<User> users = userDao.listByIds(userIds);
		for(User user:users){
			uMessage = new UserMessage();
			uMessage.setIsRead(0);
			uMessage.setMessage(msg);
			uMessage.setUser(user);
			messageDao.addObj(uMessage);
		}
		//添加并上传附件
		String[] newNames = DocumentUtil.addAttach(attachDto, attachmentDao, msg, null);
		if(isEmail > 0){
			UserEmail userEmail = userDao.loadUserEmail(SystemContext.getLoginUser().getId());
			taskExecutor.execute(new SendMailThread(msg, userIds, attachDto, newNames, SystemContext.getRealPath(), userEmail));
		}
	}
	
	private class SendMailThread implements Runnable {
		private Message message;
		private Integer[] userIds;
		private AttachDto attachDto;
		private String[] newNames;
		private String realPath;
		private UserEmail userEmail;
		
		public SendMailThread(Message message, Integer[] userIds, AttachDto attachDto, String[] newNames,
				String realPath, UserEmail userEmail) {
			this.message = message;
			this.userIds = userIds;
			this.attachDto = attachDto;
			this.newNames = newNames;
			this.realPath = realPath;
			this.userEmail = userEmail;
		}

		@Override
		public void run() {
			sendMail(userEmail, message, userIds, attachDto, newNames, realPath);
		}
	}
	
	private List<String> listContentImgUrl(String content) {
		Pattern pattern = Pattern.compile("<img.*?\\s+src=['\"](.*?)['\"].*?>");
		Matcher matcher = pattern.matcher(content);
		List<String> srcs = new ArrayList<>();
		while(matcher.find()) {
			srcs.add(matcher.group(1));
		}
		return srcs;
	}
	
	private void sendMail(UserEmail userEmail,Message msg, Integer[] userIds, AttachDto attachDto,String[] newNames,String realPath) {
		try {
			JavaMailSenderImpl jmSenderImpl = (JavaMailSenderImpl)mailSender;
			jmSenderImpl.setHost(userEmail.getHost());
			jmSenderImpl.setProtocol(userEmail.getProtocol());
			jmSenderImpl.setUsername(userEmail.getUsername());
			jmSenderImpl.setPassword(userEmail.getPassword());
			
			MimeMessage email = jmSenderImpl.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(email,true,"UTF-8");
			helper.setFrom(userEmail.getUser().getEmail());
			helper.setSubject(msg.getTitle());
			//获取收件人邮箱
			List<String> mailTos = userDao.listAllSendEmail(userIds);
			for(String toEmail : mailTos) {
				helper.addTo(toEmail);
			}
			//添加附件
			String uploadPath = attachDto.getUploadPath();
			if(attachDto.isHasAttach()) {
				File[] atts = attachDto.getAtts();
				String[] fns = attachDto.getAttaFilename();
				for(int i=0;i<atts.length;i++) {
					String fn = fns[i];
					helper.addAttachment(MimeUtility.encodeText(fn),new FileSystemResource(uploadPath+"/"+newNames[i]) );
				}
			}
			//格式化正文内容
			String content = msg.getContent();
			Map<String, String> imgMaps = new HashMap<>();	//保存cid和图片地址的关系
			List<String> imgs = listContentImgUrl(content);	//获取正文的图片链接
			int i = 0;
			for(String is : imgs) {
				imgMaps.put("x"+i, is);
				content = content.replace(is, "cid:"+("x"+i++));
			}
			helper.setText(content,true);
			Set<String> keys = imgMaps.keySet();
			for(String key : keys) {
				String url = imgMaps.get(key);
				//判断正文中的图片是来源网上还是本地
				if(url.startsWith("http://")) {
					helper.addInline(key, new URLDataSource(new URL(url)));
				} else {
					String path = realPath+"/"+url;
					helper.addInline(key, new FileSystemResource(path));
				}
			}
			jmSenderImpl.send(email);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteReceive(int msgId) {
		User user = SystemContext.getLoginUser();
		String hql = "delete UserMessage um where um.message.id=? and um.user.id = ?";
		messageDao.executeByHql(hql, new Object[]{msgId,user.getId()});
	}

	@Override
	public void deleteSend(int msgId) {
		String hql = "delete UserMessage um where um.message.id=?";
		messageDao.executeByHql(hql, msgId);
		List<Attachment> atts = this.listAttachmentByMsg(msgId);
		hql = "delete Attachment att where att.message.id=?";
		messageDao.executeByHql(hql, msgId);
		hql = "delete Message where id=?";
		messageDao.executeByHql(hql, msgId);
		String path = SystemContext.getRealPath()+"/upload";
		for(Attachment attachment:atts) {
			File file = new File(path+"/"+attachment.getNewName());
			file.delete();
		}
	}

	@Override
	public Message updateRead(int msgId, int isRead) {
		if (isRead == 0) {
			User user = SystemContext.getLoginUser();
			UserMessage userMessage = messageDao.loadUserMessage(user.getId(), msgId);
			if(userMessage.getIsRead() == 0) {
				userMessage.setIsRead(1);
				messageDao.updateObj(userMessage);
			}
		}
		return messageDao.load(msgId);
	}

	@Override
	public Pager<Message> findSendMessage(String con) {
		User user = SystemContext.getLoginUser();
		String hql = "select new Message(msg.id,msg.title,msg.content,msg.createDate) from Message msg where msg.user.id=?";
		if(con!=null&&!"".equals(con.trim())) {
			hql+=" and (msg.title like ? or msg.content like ?) order by msg.createDate desc";
			return messageDao.find(hql,new Object[]{user.getId(),"%"+con+"%","%"+con+"%"});
		}
		hql+=" order by msg.createDate desc";
		return messageDao.find(hql, user.getId());
	}

	@Override
	public Pager<Message> findReceiveMessage(String con, int isRead) {
		User user = SystemContext.getLoginUser();
		String hql = "select um.message from UserMessage um left join fetch um.message.user mu" +
				" left join fetch mu.department where um.isRead=? and um.user.id=?";
		if(con!=null&&!"".equals(con.trim())) {
			hql+=" and (um.message.title like ? or um.message.content like ?) order by um.message.createDate desc";
			return messageDao.find(hql,new Object[]{isRead,user.getId(),"%"+con+"%","%"+con+"%"});
		}
		hql+=" order by um.message.createDate desc";
		return messageDao.find(hql,new Object[]{isRead,user.getId()});
	}
	@Override
	public List<Attachment> listAttachmentByMsg(int msgId) {
		String hql = "from Attachment att where att.message.id=?";
		return attachmentDao.list(hql,msgId);
	}
	@Override
	public Message load(int msgId) {
		return messageDao.load(msgId);
	}

}
