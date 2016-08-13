package info.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import info.model.Department;
import info.service.IDepartmentService;
import info.util.ActionUtil;

@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends ActionSupport implements ModelDriven<Department> {
	private IDepartmentService departmentService;
	private Department dep;
	private int[] sdeps;
	
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public Department getDep() {
		return dep;
	}
	public void setDep(Department dep) {
		this.dep = dep;
	}
	public int[] getSdeps() {
		return sdeps;
	}
	public void setSdeps(int[] sdeps) {
		this.sdeps = sdeps;
	}
	@Override
	public Department getModel() {
		if(dep == null)
			dep = new Department();
		return dep;
	}
	
	public String list() {
		ActionContext.getContext().put("ds", departmentService.listAllDep());
		return SUCCESS;
	}
	public String addInput() {
		return SUCCESS;
	}
	public String add() {
		departmentService.add(dep);
		ActionUtil.setUrl("/department_list");
		return ActionUtil.REDIRECT;
	}
	public String delete() {
		departmentService.delete(dep.getId());
		ActionUtil.setUrl("/department_list");
		return ActionUtil.REDIRECT;
	}
	public String updateInput() {
		Department department = departmentService.load(dep.getId());
		dep.setId(department.getId());
		dep.setName(department.getName());
		return SUCCESS;
	}
	public String update() {
		Department department = departmentService.load(dep.getId());
		department.setName(dep.getName());
		departmentService.update(department);
		ActionUtil.setUrl("/department_list");
		return ActionUtil.REDIRECT;
	}
	public String setDepScopeInput() {
		dep = departmentService.load(dep.getId());
		List<Integer> ads = departmentService.listDepScopeDepId(dep.getId());
		ActionContext.getContext().put("ads", ads);
		List<Department> allDep = departmentService.listAllDep();
		allDep.remove(dep);
		ActionContext.getContext().put("ds", allDep);
		return SUCCESS;
	}
	public String setDepScope() {
		departmentService.addScopeDeps(dep.getId(), sdeps);
		ActionUtil.setUrl("/department_setDepScopeInput.action?id="+dep.getId());
		return ActionUtil.REDIRECT;
	}
	public String show() {
		dep = departmentService.load(dep.getId());
		ActionContext.getContext().put("ds", departmentService.listDepScopeDep(dep.getId()));
		return SUCCESS;
	}
	public void validateAdd(){
		if(ActionUtil.isEmpty(dep.getName())) {
			this.addFieldError("name", "部门名称不能为空");
		}
	}
	public void validateUpdate() {
		if(ActionUtil.isEmpty(dep.getName())) {
			this.addFieldError("name","部门名称不能为空");
		}
	}
}
