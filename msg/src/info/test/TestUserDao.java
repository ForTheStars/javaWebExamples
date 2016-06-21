package info.test;

import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import info.dao.DAOFactory;
import info.dao.IUserDao;
import info.model.User;

public class TestUserDao {
	
	private IUserDao userDao = DAOFactory.getUserDao();
	
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
	
	@Test
	public void testUpdate(){
		User user = userDao.load(556);
		user.setStatus(0);
		userDao.update(user);
		System.out.println(userDao.load(556).getStatus());
	}
	
}
