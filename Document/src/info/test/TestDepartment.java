package info.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.model.Department;
import info.service.IDepartmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestDepartment {
	@Resource
	private IDepartmentService departmentService;
	
	@Test
	public void testAdd() {
		Department department = new Department();
		department.setName("技术部门");
		departmentService.add(department);
	}
	
	@Test
	public void testAddDepScope() {
		departmentService.addScopeDeps(3, new int[]{1,2,4,5});
	}
	
	@Test
	public void testListByDep() {
		List<Department> departments = departmentService.listDepScopeDep(3);
		for(Department department : departments){
			System.out.println(department.getName());
		}
	}
	
	@Test
	public void testDelScopeDep() {
		departmentService.deleteScopeDep(3, 5);
	}
}
