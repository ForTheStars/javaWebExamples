package info.dao;

import java.util.List;

import info.model.Address;
import info.model.ShopException;
import info.model.User;

public class AddressDao implements IAddressDao {
	private IUserDao userDao;

	public AddressDao() {
		userDao = DAOFactory.getUserDao();
	}

	@Override
	public void add(Address address, int userId) {
		User user = userDao.load(userId);
		if(user == null)
				throw new ShopException("添加的地址用户不存在");
		address.setUser(user);
		
	}

	@Override
	public void update(Address address) {

	}

	@Override
	public void delete(int id) {

	}

	@Override
	public Address load(int id) {
		return null;
	}

	@Override
	public List<Address> list(int userId) {
		return null;
	}

}
