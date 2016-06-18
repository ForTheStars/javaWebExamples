package info.dao;

import java.util.List;

import info.model.CartProduct;
import info.model.Orders;
import info.model.Pager;
import info.model.Product;
import info.model.User;

public interface IOrdersDao {
	public void add(Orders orders,User user,int aid,List<CartProduct> cps);
	public void delete(int id);
	public void update(Orders orders);
	public void updatePrice(int id,double price);
	public void updatePayStatus(int id);
	public void updateSendStatus(int id);
	public void updateConfirmStatus(int id);
	public Orders load(int id);
	public Pager<Orders> findByUser(int userId,int status);
	public Pager<Orders> findByStatus(int status);
	
	public void addCartProduct(CartProduct cp, Orders o,Product p);
	public void deleteCartProduct(int oid);
}
