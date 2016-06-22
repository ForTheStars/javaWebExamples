package info.dao;

import info.model.Message;
import info.model.Pager;

public interface IMessageDao {
	public void add(Message message,int userId);
	public void update(Message msg);
	public void delete(int id);
	public Message load(int id);
	public Pager<Message> list();
	public int getCommentCount(int msgId);
}
