package info.dao;

import java.util.List;

import info.model.User;
import info.model.UserEmail;

public interface IUserDao extends IBaseDao<User>{
	
	//通过一组用户id来获取一组用户对象
	public List<User> listByIds(Integer[] userIds);
	
	//获取某个用户可以发送信息的用户
	public List<User> listAllCanSendUser(int userId);
	
	//通过用户的id获取相应的邮件信息
	public UserEmail loadUserEmail(int id);
	
	//获取一组用户的邮箱信息
	public List<String> listAllSendEmail(Integer[] userIds);
	
}
