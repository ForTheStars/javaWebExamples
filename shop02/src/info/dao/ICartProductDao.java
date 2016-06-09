package info.dao;

import info.model.CartProduct;

public interface ICartProductDao {
	public void add(CartProduct cp,int oid);
	public void delete(int oid);
}
