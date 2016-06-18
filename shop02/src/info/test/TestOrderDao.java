package info.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import info.dao.AddressDao;
import info.dao.IOrdersDao;
import info.dao.UserDao;
import info.model.Address;
import info.model.CartProduct;
import info.model.Orders;
import info.model.ShopDi;
import info.model.User;

public class TestOrderDao extends BaseTest {
	private IOrdersDao ordersDao;
	private UserDao userDao;
	private AddressDao addressDao;
	
	public IOrdersDao getOrdersDao() {
		return ordersDao;
	}
	@ShopDi
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	@ShopDi
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public AddressDao getAddressDao() {
		return addressDao;
	}
	@ShopDi
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}
	
	
	@Test
	public void TestAdd() {
		Orders orders = new Orders();
		List<CartProduct> cps = new ArrayList<CartProduct>();
		orders.setBuyDate(new Date(116,7,14));
		orders.setStatus(3);
		orders.setPrice(99.99);
		ordersDao.add(orders, userDao.load(4), 1, cps);
		
	}
	
	@Test
	public void testLoad() {
		Orders o = ordersDao.load(1);
		System.out.println(o.getPrice()+","+o.getStatus()+","+o.getAddress().getName()+","+o.getUser().getNickname());
		for(CartProduct cp:o.getProducts()) {
			System.out.println(cp.getProduct().getName()+","+cp.getNumber());
		}
	}
	

}
