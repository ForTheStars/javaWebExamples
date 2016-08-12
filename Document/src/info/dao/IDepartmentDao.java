package info.dao;

import java.util.List;

import info.model.Department;

public interface IDepartmentDao extends IBaseDao<Department> {
	
	//根据部门来获取所有的可以发文的部门id列表
	public List<Integer> listAllExistIds(int depId);
	
	public List<Department> listByIds(Integer[]ids);
	
	//通过用户id获取可以发文的部门，使用SQL语句
	public List<Department> listSendDepByUser(int id);
}
