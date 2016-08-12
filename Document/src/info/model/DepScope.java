package info.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * 部门范围(联系)表
 * 表示一个部门是否与其他部门有沟通(可发公文)
 */
@Entity
@Table(name="t_dep_scope")
public class DepScope {
	private int id;
	private int depID;	//要设置的部门id
	private Department scopeDep;	//可以发送信息的部门对象
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="dep_id")
	public int getDepID() {
		return depID;
	}
	public void setDepID(int depID) {
		this.depID = depID;
	}
	@ManyToOne
	@JoinColumn(name="s_dep_id")
	public Department getScopeDep() {
		return scopeDep;
	}
	public void setScopeDep(Department scopeDep) {
		this.scopeDep = scopeDep;
	}
	
}
