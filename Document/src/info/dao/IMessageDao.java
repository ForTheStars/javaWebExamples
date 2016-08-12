package info.dao;

import info.model.Message;
import info.model.UserMessage;

public interface IMessageDao extends IBaseDao<Message>{
	
	//检查某个用户是否已经读过该msg
	public boolean checkIsRead(int userId,int msgId);
	
	//根据用户id和私人信件id加载UserMessage对象
	public UserMessage loadUserMessage(int userId,int msgId);
}
