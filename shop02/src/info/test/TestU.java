package info.test;

import static org.junit.Assert.*;

import org.junit.Test;

import info.dao.DAOFactory;
import info.dao.IUserDao;
import info.model.User;

public class TestU {

	private IUserDao ud = DAOFactory.getUserDao();
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAdd() {
		User user = new User();
		user.setNickname("呵");
		user.setPassword("23333");
		user.setType(1);
		user.setUsername("hhh");
		ud.add(user);
	}

}
