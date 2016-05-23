package info.dao;

import info.model.Pager;
import info.model.User;

public interface IUserDao {
	public void add(User user);
	public void delect(int id);
	public void update(User user);
	public User loadByUsername(String username);
	public User load(int id);
	public Pager<User> find(String name);
	public User login(String username, String password);
}
