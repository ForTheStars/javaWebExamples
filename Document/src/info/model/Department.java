package info.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 部门类
 */
@Entity
@Table(name="t_department")
public class Department {
	private int id;
	private String name;
	
	public Department() {
	}
	public Department(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		return 100000+id;
	}
	@Override
	public boolean equals(Object obj) {
		Department department = (Department)obj;
		if(department.getId() == this.id)
			return true;
		return false;
	}
}
