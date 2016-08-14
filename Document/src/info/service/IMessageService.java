package info.service;

import java.io.IOException;
import java.util.List;

import info.dto.AttachDto;
import info.model.Attachment;
import info.model.Message;
import info.model.Pager;

public interface IMessageService {
	public void add(Message msg,Integer[] userIds,AttachDto attachDto,int isEmail)throws IOException;
	public Message load(int msgId);
	public void deleteReceive(int msgId);
	public void deleteSend(int msgId);
	public Message updateRead(int msgId,int isRead);
	public Pager<Message> findSendMessage(String con);
	public Pager<Message> findReceiveMessage(String con,int isRead);
	public List<Attachment> listAttachmentByMsg(int msgId);
}
