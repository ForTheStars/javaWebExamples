package info.service;

import java.util.List;

import info.model.Pager;
import info.model.User;
import info.model.UserEmail;

public interface IUserService {
	public void add(User user,int depId);
	public void addUserEmail(UserEmail userEmail,int userId);
	public void updateUserEmail(UserEmail userEmail);
	public void delete(int id);
	public void update(User user,int depId);
	public void update(User user);
	public User load(int id);
	public UserEmail loadUserEmail(int id);
	public Pager<User> findUserByDep(Integer depId);
	public User login(String username,String password);
	public List<User> listAllSendUser(int userId);
	public List<String> listAllSendEmail(Integer[] userIds);
}
