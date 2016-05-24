package info.test;

import info.dao.DAOFactory;
import info.dao.IAddressDao;
import info.model.Address;

import java.util.List;

import org.junit.Test;

public class TestAddressDao {
	private IAddressDao addressDao = DAOFactory.getAddressDao();
	
	
	@Test
	public void testLoad() {
		Address address = addressDao.load(2);
		System.out.println(address.getName()+","+address.getUser()+","+address.getPostcode());
	}
	
	@Test
	public void testAdd() {
		Address address = new Address();
		address.setName("浙江省义乌市");
		address.setPhone("114");
		address.setPostcode("652000");
		addressDao.add(address, 2);
	}
	
	@Test
	public void testList() {
		List<Address> list = addressDao.list(1);
		for(Address a:list) {
			System.out.println(a.getName()+","+a.getUser());
		}
	}
}
