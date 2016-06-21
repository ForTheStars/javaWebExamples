package info.dao;

import info.model.Pager;
import info.model.User;

public interface IUserDao {
	public void add(User user);
	public void delete(int id);
	public void update(User user);
	public User load(int id);
	public Pager<User> list(String condition);
	public User login(String username,String password);
}
