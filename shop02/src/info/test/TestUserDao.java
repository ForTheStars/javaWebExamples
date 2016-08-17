package info.test;

import static org.junit.Assert.*;

import org.junit.Test;

import info.dao.IAddressDao;
import info.dao.IUserDao;
import info.model.Address;
import info.model.Pager;
import info.model.ShopDi;
import info.model.SystemContext;
import info.model.User;
import info.util.DaoUtil;

public class TestUserDao extends BaseTest {
	private IUserDao ud;
	
	public IUserDao getUd() {
		return ud;
	}
	@ShopDi("userDao")
	public void setUd(IUserDao ud) {
		this.ud = ud;
	}

	@Test
	public void testAdd() {
		User user = new User();
		user.setNickname("呵呵");
		user.setPassword("23333");
		user.setType(1);
		user.setUsername("hh");
		ud.add(user);
	}
	
	@Test
	public void testUpdate(){
		User user = ud.loadByUsername("xiaoxiao");
		user.setPassword("888");
		ud.update(user);
		System.out.println(ud);
	}
	
	@Test
	public void testDelect(){
		ud.delete(2);
	}
	
	@Test
	public void testLogin(){
		User user = ud.login("dada","23333");
		System.out.println(user.getClass().getName());
		System.out.println(user.getNickname());
	}
	
	@Test
	public void testFind(){
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setOrder("desc");
		SystemContext.setSort("nickname");
		Pager<User> ps = ud.find("上");
		System.out.println(ps.getTotalRecord());
		for(User u:ps.getDatas()) {
			System.out.println(u);
		}
	}
	
	@Test
	public void testLoad(){
		User user = ud.load(1);
		for(Address a:user.getAddresses()){
			System.out.println(a);
		}
	}
	
	@Test
	public void testSingle(){
		IUserDao ud1 = (IUserDao)DaoUtil.createDaoFactory().getDao("userDao");
		IUserDao ud2 = (IUserDao)DaoUtil.createDaoFactory().getDao("userDao");
		System.out.println(ud1==ud2);
		IAddressDao ad1 = (IAddressDao)DaoUtil.createDaoFactory().getDao("addressDao");
		IAddressDao ad2 = (IAddressDao)DaoUtil.createDaoFactory().getDao("addressDao");
		System.out.println(ad2==ad1);
	}
	
}
