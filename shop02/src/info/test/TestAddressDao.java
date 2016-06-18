package info.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.sun.prism.shader.Texture_Color_AlphaTest_Loader;

import info.dao.AddressDao;
import info.dao.IAddressDao;
import info.dao.IUserDao;
import info.model.Address;
import info.model.ShopDi;

public class TestAddressDao extends BaseTest {
	private IAddressDao addressDao;
	private IUserDao userDao;
	
	public IAddressDao getAddressDao() {
		return addressDao;
	}
	@ShopDi
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
		
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@ShopDi
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Test
	public void TestUserDao(){
		new AddressDao();
	}
	
	@Test
	public void testLoad() {
		Address address = addressDao.load(1);
		System.out.println(address.getName()+","+address.getUser()+","+address.getPostcode());
	}
	
	@Test
	public void testAdd(){
		Address address = new Address();
		address.setName("浙江省东阳市");
		address.setPhone("32323");
		address.setPostcode("32400");
		addressDao.add(address, 6);
	}
	
	@Test
	public void testList(){
		List<Address> list = addressDao.list(1);
		for(Address a:list){
			System.out.println(a.getName()+","+a.getUser());
		}
	}

}
