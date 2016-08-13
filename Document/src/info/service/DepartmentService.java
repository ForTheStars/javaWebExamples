package info.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.xml.internal.bind.v2.runtime.SchemaTypeTransducer;

import info.dao.IDepartmentDao;
import info.model.DepScope;
import info.model.Department;

@Service("departmentService")
public class DepartmentService implements IDepartmentService {
	private IDepartmentDao departmentDao;
	
	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public void add(Department department) {
		departmentDao.add(department);
	}

	@Override
	public void update(Department department) {
		departmentDao.update(department);
	}

	@Override
	public void delete(int id) {
		//1、判断是否有用户，如果有抛出异常
		//2、需要将部门之间的关联信息删除
		String hql = "delete DepScope ds where ds.depId = ? or ds.scopeDep.id = ?";
		departmentDao.executeByHql(hql, new Object[]{id,id});
		departmentDao.delete(id);
	}

	@Override
	public Department load(int id) {
		return departmentDao.load(id);
	}

	@Override
	public List<Department> listAllDep() {
		return departmentDao.list("from Department");
	}

	@Override
	public void addScopeDep(int depId, int sDepId) {
		String hql = "select ds from DepScope ds where ds.depId = ? and ds.scopeDep.id=?";
		DepScope depScope = (DepScope)this.departmentDao.queryByHql(hql,new Object[]{depId,sDepId});
		if(depScope != null)
			return;
		depScope = new DepScope();
		depScope.setDepId(depId);
		depScope.setScopeDep(departmentDao.load(sDepId));
		departmentDao.addObj(depScope);
	}

	@Override
	public void addScopeDeps(int depId, int[] sDepIds) {
		List<Integer> sDepIdList = new ArrayList<>();
		for(int sid:sDepIds){
			sDepIdList.add(sid);
		}
		List<Integer> esd = departmentDao.listAllExistIds(depId);
		List<Integer> delIds = getDelDeps(esd, sDepIdList);
		List<Integer> addIds = getAddDeps(esd, sDepIdList);
		for(int id:delIds) {
			this.deleteScopeDep(depId, id);
		}
		for(int sDepId:addIds) {
			this.addScopeDep(depId, sDepId);
		}
	}

	@Override
	public void deleteScopeDep(int depId, int sDepId) {
		String hql = "delete DepScope ds where ds.depId = ? and ds.scopeDep.id=?";
		departmentDao.executeByHql(hql, new Object[]{depId,sDepId});
	}

	@Override
	public void deleteScopeDeps(int depId) {
		String hql = "delete DepScope ds where ds.depId=?";
		departmentDao.executeByHql(hql, new Object[]{depId});
	}

	@Override
	public List<Department> listUserDep(int userId) {
		return departmentDao.listSendDepByUser(userId);
	}

	@Override
	public List<Department> listDepScopeDep(int depId) {
		String hql = "select dep from DepScope ds left join ds.scopeDep dep where ds.depId=?";
		return departmentDao.list(hql, depId);
	}

	@Override
	public List<Integer> listDepScopeDepId(int depId) {
		return departmentDao.listAllExistIds(depId);
	}
	
	private List<Integer> getDelDeps(List<Integer> esd,List<Integer> sDepIds) {
		List<Integer> result = new ArrayList<>();
		for(int id:esd){
			if(!sDepIds.contains(id)){
				result.add(id);
			}
		}
		return result;
	}
	
	private List<Integer> getAddDeps(List<Integer> esd,List<Integer> sDepIds) {
		List<Integer> result = new ArrayList<Integer>();
		for(int id:sDepIds) {
			if(!esd.contains(id)) {
				result.add(id);
			}
		}
		return result;
	}
}
