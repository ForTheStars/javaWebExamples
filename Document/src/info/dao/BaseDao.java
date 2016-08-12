package info.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import info.model.Pager;
import info.model.SystemContext;

public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {
	private Class<T> clz;
	
	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void add(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void addObj(Object obj) {
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public void updateObj(Object obj) {
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(int id) {
		T t = this.load(id);
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public void delete(Object obj) {
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public T load(int id) {
		return this.getHibernateTemplate().load(getClz(), id);
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		Query query = setParamterQuery(hql, args);
		return query.list();
	}

	@Override
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[]{arg});
	}

	@Override
	public List<T> list(String hql) {
		return this.list(hql, null);
	}

	@Override
	public List<Object> listByObj(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		return q.list();
	}

	@Override
	public List<Object> listByObj(String hql, Object arg) {
		return this.listByObj(hql, new Object[]{arg});
	}

	@Override
	public List<Object> listByObj(String hql) {
		return this.listByObj(hql,null);
	}

	@Override
	public Pager<T> find(String hql, Object[] args) {
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		if(pageSize <= 0)
			pageSize = 10;
		if(pageOffset<0)
			pageOffset = 0;
		Query query = setParamterQuery(hql, args);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
		Query countQuery = setParamterQuery(getCountHql(hql), args);
		Pager<T> pager = new Pager<>();
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setDatas((List<T>)query.list());
		pager.setTotalRecord((Long)countQuery.uniqueResult());
		return pager;
	}

	@Override
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[]{arg});
	}

	@Override
	public Pager<T> find(String hql) {
		return this.find(hql,null);
	}

	@Override
	public Pager<Object> findByObj(String hql, Object[] args) {
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		if(pageSize <= 0)
			pageSize = 10;
		if(pageOffset<0)
			pageOffset = 0;
		Query query = this.setParamterQuery(hql, args);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
		Query countQuery = this.setParamterQuery(getCountHql(hql), args);
		Pager<Object> pager= new Pager<>();
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setDatas((List<Object>)query.list());
		pager.setTotalRecord((Long)countQuery.uniqueResult());
		return pager;
	}

	@Override
	public Pager<Object> findByObj(String hql, Object arg) {
		return this.findByObj(hql, new Object[]{arg});
	}

	@Override
	public Pager<Object> findByObj(String hql) {
		return this.findByObj(hql,null);
	}

	@Override
	public Object queryByHql(String hql, Object[] args) {
		Query query = setParamterQuery(hql, args);
		return query.uniqueResult();
	}

	@Override
	public Object queryByHql(String hql, Object arg) {
		return this.queryByHql(hql, new Object[]{arg});
	}

	@Override
	public Object queryByHql(String hql) {
		return this.queryByHql(hql,null);
	}

	@Override
	public void executeByHql(String hql, Object[] args) {
		Query query = setParamterQuery(hql, args);
		query.executeUpdate();
	}

	@Override
	public void executeByHql(String hql, Object arg) {
		this.executeByHql(hql,new Object[]{arg});
	}

	@Override
	public void executeByHql(String hql) {
		this.executeByHql(hql,null);
	}
	
	private Query setParamterQuery(String hql,Object[] args) {
		Query query = this.getSession().createQuery(hql);
		if(args != null) {
			for(int i = 0;i < args.length;i++){
				query.setParameter(i, args[i]);
			}
		}
		return query;
	}
	
	private Class<T> getClz(){
		if(clz == null){
			clz = ((Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	
	private String getCountHql(String hql) {
		hql = "select count(*) "+hql.substring(hql.indexOf("from"));
		return hql.replace("fetch ", "");
	}
}
