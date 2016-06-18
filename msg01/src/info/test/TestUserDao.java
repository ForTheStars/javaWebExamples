package info.test;

import static org.junit.Assert.*;

import org.junit.Test;

import info.dao.DAOFactory;
import info.dao.IUserDao;
import info.dao.UserDao;
import info.model.User;

public class TestUserDao {
	private IUserDao userDao = DAOFactory.getUserDao();
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testLogin(){
		User user = userDao.login("admin", "123");
		System.out.println(user.getNickname());
	}
	
	@Test
	public void testLoad(){
		User user = userDao.load(1);
		System.out.println(user.getNickname()+" "+user.getPassword());
	}
}
