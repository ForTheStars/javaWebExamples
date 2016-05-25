package info.dao;

import java.util.List;

import info.model.Address;

public class AddressOracleDao implements IAddressDao {

	@Override
	public void add(Address address, int userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Address address) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Address load(int id) {
		System.out.println("oracle");
		return null;
	}

	@Override
	public List<Address> list(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
