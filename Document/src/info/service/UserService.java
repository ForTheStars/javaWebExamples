package info.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import info.dao.IDepartmentDao;
import info.dao.IUserDao;
import info.exception.DocumentException;
import info.model.Department;
import info.model.Pager;
import info.model.User;
import info.model.UserEmail;

@Service("userService")
public class UserService implements IUserService {
	private IUserDao userDao;
	private IDepartmentDao departmentDao;
	
	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public void add(User user, int depId) {
		if(loadByUsername(user.getUsername()) != null){
			throw new DocumentException("要添加的用户已经存在不能添加！");
		}
		Department department = departmentDao.load(depId);
		user.setDepartment(department);
		userDao.add(user);
	}

	@Override
	public void addUserEmail(UserEmail userEmail, int userId) {
		User user = this.load(userId);
		userEmail.setUser(user);
		userDao.addObj(userEmail);
	}

	@Override
	public void updateUserEmail(UserEmail userEmail) {
		userDao.updateObj(userEmail);
	}

	@Override
	public void delete(int id) {
		// TODO 需要加入删除关联对象的代码，需要关联删除文档、消息对象
		userDao.delete(id);
	}

	@Override
	public void update(User user, int depId) {
		Department department = departmentDao.load(depId);
		user.setDepartment(department);
		userDao.update(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}

	@Override
	public UserEmail loadUserEmail(int id) {
		return userDao.loadUserEmail(id);
	}

	@Override
	public Pager<User> findUserByDep(Integer depId) {
		Pager<User> users = null;
		if(depId == null || depId <= 0){
			users = userDao.find("from User u left join fetch u.department");
		} else {
			users = userDao.find("from User u left join fetch u.department dep where dep.id=?",depId);
		}
		return users;
	}

	@Override
	public User login(String username, String password) {
		User user = this.loadByUsername(username);
		if(user == null){
			throw new DocumentException("用户名不存在");
		}
		if(!user.getPassword().equals(password)){
			throw new DocumentException("用户密码不正确");
		}
		return user;
	}

	@Override
	public List<User> listAllSendUser(int userId) {
		return userDao.listAllCanSendUser(userId);
	}

	@Override
	public List<String> listAllSendEmail(Integer[] userIds) {
		return userDao.listAllSendEmail(userIds);
	}
	
	private User loadByUsername(String username){
		String hql = "select u from User u where u.username = ?";
		return (User)userDao.queryByHql(hql, username);
	}
}
