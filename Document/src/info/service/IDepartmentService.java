package info.service;

import java.util.List;

import info.model.Department;

public interface IDepartmentService {
	public void add(Department department);
	public void update(Department department);
	public void delete(int id);
	public Department load(int id);
	//获取所有部门
	public List<Department> listAllDep();
	//为某个部门添加一个关联部门
	public void addScopeDep(int depId,int sDepId);
	//为某个部门添加一组关联部门
	public void addScopeDeps(int depId,int[] sDepIds);
	//删除某个部门的关联部门
	public void deleteScopeDep(int depId,int sDepId);
	//删除某个部门(删除所有关联部门)
	public void deleteScopeDeps(int depId);
	//获取某个员工(用户)的管理部门
	public List<Department> listUserDep(int userId);
	//获取某个部门的关联部门
	public List<Department> listDepScopeDep(int depId);
	//获取某个部门的关联部门Id
	public List<Integer> listDepScopeDepId(int depId);
}
