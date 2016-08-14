package info.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import info.dao.IAttachmentDao;
import info.dao.IMessageDao;
import info.dao.IUserDao;
import info.dto.AttachDto;
import info.model.Attachment;
import info.model.Message;
import info.model.Pager;
import info.model.SystemContext;
import info.model.User;
import info.model.UserMessage;
import info.util.DocumentUtil;

@Service("messageService")
public class MessageService implements IMessageService {
	private IMessageDao messageDao;
	private IUserDao userDao;
	private IAttachmentDao attachmentDao;
	
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
		//TODO	
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
