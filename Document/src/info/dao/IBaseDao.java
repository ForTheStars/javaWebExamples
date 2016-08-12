package info.dao;

import java.util.List;

import info.model.Pager;

public interface IBaseDao<T> {
	public void add(T t);
	public void addObj(Object obj);
	public void update(T t);
	public void updateObj(Object obj);
	public void delete(int id);
	public void delete(Object obj);
	public T load(int id);
	
	//通过hql获取一组对象，不进行分页
	public List<T> list(String hql,Object[] args);
	public List<T> list(String hql,Object arg);
	public List<T> list(String hql);
	
	public List<Object> listByObj(String hql,Object[] args);
	public List<Object> listByObj(String hql,Object arg);
	public List<Object> listByObj(String hql);
	
	//通过hql获取一组对象，进行分页处理
	public Pager<T> find(String hql,Object[] args);
	public Pager<T> find(String hql,Object arg);
	public Pager<T> find(String hql);
	
	public Pager<Object> findByObj(String hql,Object[] args);
	public Pager<Object> findByObj(String hql,Object arg);
	public Pager<Object> findByObj(String hql);
	
	//通过hql获取一个对象
	public Object queryByHql(String hql,Object[] args);
	public Object queryByHql(String hql,Object arg);
	public Object queryByHql(String hql);
	
	//调用hql更新一组对象
	public void executeByHql(String hql,Object[] args);
	public void executeByHql(String hql,Object arg);
	public void executeByHql(String hql);
}
