package info.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import info.model.Category;
import info.model.Pager;
import info.model.Product;
import info.model.ShopDi;
import info.model.ShopException;
import info.model.SystemContext;

public class ProductDao extends BaseDao<Product> implements IProductDao {
	private ICategoryDao categoryDao;
	
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}
	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public void add(int cid,Product product) {
		Category c = categoryDao.load(cid);
		if(c==null) throw new ShopException("要添加产品的类别不存在!");
		product.setCategory(c);
		super.add(product);
	}

	@Override
	public void update(int cid,Product product) {
		Category c = categoryDao.load(cid);
		if(c==null) throw new ShopException("要添加产品的类别不存在!");
		product.setCategory(c);
		super.update(product);
	}

	@Override
	public void delete(int id) {
		// TODO 如果用户购买了该商品就不能删除，该商品存在订单也不能删除，
		//如果可以删除商品的话需要删除商品的图片
		Product p = this.load(id);
		String img = p.getImg();
		super.delete(Product.class, id);
		//删除图片
		String path = SystemContext.getRealpath()+"/img/";
		File f = new File(path+img);
		f.delete();
	}

	@Override
	public Product load(int id) {
		return super.load(Product.class, id);
	}

	@Override
	public Pager<Product> find(int cid, String name,int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(cid>0) {
			params.put("cid", cid);
		}
		if(name!=null&&!"".equals(name.trim())) {
			params.put("name", "%"+name+"%");
		}
		if(status==1||status==-1) {
			params.put("status", status);
		}
		return super.find(Product.class, params);
	}

	@Override
	public void addStock(int id, int num) {
		Product p = this.load(id);
		p.setStock(p.getStock()+num);
		this.update(p);
	}

	@Override
	public void decreaseStock(int id, int num) {
		Product p = this.load(id);
		p.setStock(p.getStock()-num);
		this.update(p);
	}
	@Override
	public void changeStatus(int id) {
		Product p = this.load(id);
		if(p.getStatus()==-1) {
			p.setStatus(1);
		} else {
			p.setStatus(-1);
		}
		this.update(p);
	}

}
