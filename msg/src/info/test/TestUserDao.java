package info.test;

import org.junit.Test;

import info.dao.DAOFactory;
import info.dao.IUserDao;
import info.model.MsgException;
import info.model.Pager;
import info.model.SystemContext;
import info.model.User;

public class TestUserDao {
	
	private IUserDao userDao = DAOFactory.getUserDao();
	
	@Test
	public void testAdd() {
		User user = new User();
		user.setUsername("test");
		user.setNickname("测试");
		user.setPassword("123");
		user.setStatus(1);
		user.setType(1);
		userDao.add(user);
	}
	
	@Test
	public void testLogin(){
		User user = userDao.login("test", "123");
		System.out.println(user.getNickname());
	}

	@Test(expected=MsgException.class)
	public void testLoginException() {
		User user = userDao.login("test", "321");
	}
	
	@Test
	public void testLoad(){
		User user = userDao.load(1);
		System.out.println(user.getNickname()+" "+user.getPassword());
	}
	
	@Test
	public void testUpdate(){
		User user = userDao.load(1);
		user.setUsername("admin");
		user.setNickname("管理员");
		user.setPassword("321");
		userDao.update(user);
		System.out.println(userDao.load(1).getStatus());
	}
	
	@Test
	public void testDelete(){
		userDao.delete(2);
	}
	
	@Test
	public void testList() {
		SystemContext.setPageIndex(1);
		SystemContext.setPageSize(15);
		Pager<User> ps = userDao.list("管");
		System.out.println(ps.getTotalRecord());
		for(User u:ps.getDatas()) {
			System.out.println(u);
		}
	}
}
