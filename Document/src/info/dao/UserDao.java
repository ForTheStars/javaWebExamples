package info.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import info.model.User;
import info.model.UserEmail;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public List<User> listByIds(Integer[] userIds) {
		Query q = this.getSession().createQuery("select new User(u.id) from User u where u.id in (:ids)");
		q.setParameterList("ids",userIds);
		return q.list();
	}

	@Override
	public List<User> listAllCanSendUser(int userId) {
		String sql = "select t3.id,t3.nickname from t_user t1 left join t_dep_scope t2 on(t1.dep_id=t2.dep_id)" +
				" right join t_user t3 on(t2.s_dep_id=t3.dep_id) where t1.id=?";
		return this.getSession().createSQLQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(User.class)).setParameter(0, userId).list();
	}

	@Override
	public UserEmail loadUserEmail(int id) {
		String hql = "select ue from UserEmail ue where ue.user.id=?";
		return (UserEmail)this.queryByHql(hql, id);
	}

	@Override
	public List<String> listAllSendEmail(Integer[] userIds) {
		String hql = "select u.email from User u where u.id in (:ids) and u.email is not null";
		List<String> list = this.getSession().createQuery(hql).setParameterList("ids", userIds).list();
		return list;
	}
}
