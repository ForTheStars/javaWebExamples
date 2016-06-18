package info.dao;

import info.model.Pager;
import info.model.Product;

public interface IProductDao {
	public void add(int cid,Product product);
	public void update(int cid,Product product);
	public void delete(int id);
	public Product load(int id);
	/**
	 * 可以通过商品类别和名称进行搜索
	 * 此时可以进行灵活的排序
	 * @param cid
	 * @param name
	 * @return
	 */
	public Pager<Product> find(int cid,String name,int status);
	/**
	 * 增加库存
	 * @param id
	 * @param num
	 */
	public void addStock(int id,int num);
	/**
	 * 减少库存
	 * @param id
	 * @param num
	 */
	public void decreaseStock(int id,int num);
	/**
	 * 变更状态
	 * @param id
	 */
	public void changeStatus(int id);
}
