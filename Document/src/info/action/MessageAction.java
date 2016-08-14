package info.action;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import info.dto.AttachDto;
import info.model.Message;
import info.model.SystemContext;
import info.model.User;
import info.service.IMessageService;
import info.service.IUserService;
import info.util.ActionUtil;

@Controller("messageAction")
@Scope("prototype")
public class MessageAction extends ActionSupport implements ModelDriven<Message> {
	private IMessageService messageService;
	private IUserService userService;
	private Message msg;
	private int isRead;
	private String con;
	private Integer[] userIds;
	private File[] atts;
	private String[] attsContentType;
	private String[] attsFileName;
	private int isEmail;
	
	public IMessageService getMessageService() {
		return messageService;
	}
	@Resource
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	public IUserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public Message getMsg() {
		return msg;
	}
	public void setMsg(Message msg) {
		this.msg = msg;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public Integer[] getUserIds() {
		return userIds;
	}
	public void setUserIds(Integer[] userIds) {
		this.userIds = userIds;
	}
	public File[] getAtts() {
		return atts;
	}
	public void setAtts(File[] atts) {
		this.atts = atts;
	}
	public String[] getAttsContentType() {
		return attsContentType;
	}
	public void setAttsContentType(String[] attsContentType) {
		this.attsContentType = attsContentType;
	}
	public String[] getAttsFileName() {
		return attsFileName;
	}
	public void setAttsFileName(String[] attsFileName) {
		this.attsFileName = attsFileName;
	}
	public int getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}
	@Override
	public Message getModel() {
		if(msg == null)
			msg = new Message();
		return msg;
	}
	
	public String addInput() {
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		ActionContext.getContext().put("us", userService.listAllSendUser(user.getId()));
		return SUCCESS;
	}
	
	public String add() throws IOException {
		if(atts == null || atts.length <= 0) {
			messageService.add(msg, userIds, new AttachDto(false), isEmail);
		} else {
			String uploadPath = SystemContext.getRealPath()+"\\upload";
			messageService.add(msg, userIds, new AttachDto(atts,attsContentType,attsFileName,uploadPath), isEmail);
		}
		ActionUtil.setUrl("message_listSend");
		return ActionUtil.REDIRECT;
	}
	
	public String show() throws IllegalAccessException, InvocationTargetException {
		Message message = messageService.updateRead(msg.getId(), isRead);
		BeanUtils.copyProperties(msg, message);
		ActionContext.getContext().put("atts", messageService.listAttachmentByMsg(msg.getId()));
		return SUCCESS;
	}
	
	public String showSend() throws IllegalAccessException, InvocationTargetException {
		Message message = messageService.load(msg.getId());
		BeanUtils.copyProperties(msg, message);
		ActionContext.getContext().put("atts", messageService.listAttachmentByMsg(msg.getId()));
		return SUCCESS;
	}
	
	public String listSend() {
		ActionContext.getContext().put("pages", messageService.findSendMessage(con));
		return SUCCESS;
	}
	
	public String listReceive() {
		ActionContext.getContext().put("pages", messageService.findReceiveMessage(con, isRead));
		return SUCCESS;
	}
	
	public String deleteSend() {
		messageService.deleteSend(msg.getId());
		ActionUtil.setUrl("/message_listSend");
		return ActionUtil.REDIRECT;
	}
	
	public String deleteReceive() {
		messageService.deleteReceive(msg.getId());
		ActionUtil.setUrl("/message_listReceive");
		return ActionUtil.REDIRECT;
	}
	
	public void validateAdd() {
		if(userIds==null||userIds.length<=0) {
			this.addFieldError("userIds", "必须选择要发送的用户");
		}
		if(msg.getTitle()==null||"".equals(msg.getTitle())) {
			this.addFieldError("title","私人信件的标题不能为空");
		}
		if(this.hasFieldErrors()) {
			addInput();
		}
	}
}
