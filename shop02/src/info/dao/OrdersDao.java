package info.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.model.Address;
import info.model.CartProduct;
import info.model.Orders;
import info.model.Pager;
import info.model.Product;
import info.model.ShopDi;
import info.model.ShopException;
import info.model.User;

public class OrdersDao extends BaseDao<Orders> implements IOrdersDao {
	
	private IAddressDao addressDao;
	private IProductDao productDao;
	
	
	
	public IProductDao getProductDao() {
		return productDao;
	}

	@ShopDi
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}


	public IAddressDao getAddressDao() {
		return addressDao;
	}


	@ShopDi
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}


	@Override
	public void add(Orders orders, User user, int aid, List<CartProduct> cps) {
		Address a = addressDao.load(aid);
		orders.setAddress(a);
		orders.setUser(user);
		super.add(orders);
		//接着添加购物对象
		for(CartProduct cp:cps) {
			this.addCartProduct(cp, orders, cp.getProduct());
		}
	}

	@Override
	public void delete(int id) {
		this.deleteCartProduct(id);
		Orders o = this.load(id);
		if(o.getStatus()!=1) throw new ShopException("只能删除未付款的订单");
		super.delete(Orders.class, id);
	}

	@Override
	public void update(Orders orders) {
		super.update(orders);
	}

	@Override
	public void updatePrice(int id,double price) {
		Orders o = this.load(id);
		o.setPrice(price);
		this.update(o);
	}

	@Override
	public void updatePayStatus(int id) {
		Orders o = this.load(id);
		List<CartProduct> cps = o.getProducts();
		List<Product> ps = new ArrayList<Product>();
		for(CartProduct cp:cps) {
			int num = cp.getNumber();
			Product p = productDao.load(cp.getProduct().getId());
			if(p.getStock()<num) {
				throw new ShopException("要买的"+p.getName()+"库存不足");
			} else {
				p.setStock(p.getStock()-num);
				ps.add(p);
			}
		}
		//更新库存
		for(Product pp:ps) {
			productDao.update(pp.getCategory().getId(), pp);
		}
		o.setStatus(2);
		o.setPayDate(new Date());
		this.update(o);
	}

	@Override
	public void updateSendStatus(int id) {
		Orders o = this.load(id);
		o.setStatus(3);
		this.update(o);
	}

	@Override
	public void updateConfirmStatus(int id) {
		Orders o = this.load(id);
		o.setStatus(4);
		o.setConfirmDate(new Date());
		this.update(o);
	}

	@Override
	public Orders load(int id) {
		return super.load(Orders.class, id);
	}

	@Override
	public Pager<Orders> findByUser(int userId, int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		if(status>0) {
			params.put("status", status);
		}
		return super.find(Orders.class.getName()+".find_by_user", params);
	}

	@Override
	public Pager<Orders> findByStatus(int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(status>0) {
			params.put("status", status);
		}
		return super.find(Orders.class.getName()+".find_by_status", params);
	}

	@Override
	public void addCartProduct(CartProduct cp, Orders o,Product p) {
		cp.setOrders(o);
		cp.setProduct(p);
		super.add(CartProduct.class.getName()+".add", cp);
	}

	@Override
	public void deleteCartProduct(int oid) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("oid", oid);
		super.delete(CartProduct.class.getName()+".deleteByOrders", params);
	}

}
