package info.action;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import info.model.User;
import info.model.UserEmail;
import info.service.IDepartmentService;
import info.service.IUserService;
import info.util.ActionUtil;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User> {
	private User user;
	private int userId;
	private int update;
	private Integer depId;
	private IUserService userService;
	private IDepartmentService departmentService;
	private UserEmail userEmail;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUpdate() {
		return update;
	}
	public void setUpdate(int update) {
		this.update = update;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public IUserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public UserEmail getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(UserEmail userEmail) {
		this.userEmail = userEmail;
	}
	
	public String addEmailInput() {
		User loginUser = (User)ActionContext.getContext().getSession().get("loginUser");
		UserEmail uEmail = userService.loadUserEmail(loginUser.getId());
		if(uEmail == null) {
			update = 0;
		} else {
			update = 1;
			userEmail = uEmail;
		}
		return SUCCESS;
	}
	
	public String addEmail() throws IllegalAccessException, InvocationTargetException {
		if(update == 0) {
			userService.addUserEmail(userEmail, userId);
		} else {
			UserEmail uEmail = userService.loadUserEmail(userId);
			uEmail.setHost(userEmail.getHost());
			uEmail.setPassword(userEmail.getPassword());
			uEmail.setProtocol(userEmail.getProtocol());
			uEmail.setUsername(userEmail.getUsername());
			userService.updateUserEmail(uEmail);
		}
		ActionUtil.setUrl("/user_showSelf");
		return ActionUtil.REDIRECT;
	}
	
	public String list() {
		ActionContext.getContext().put("ds",departmentService.listAllDep());
		ActionContext.getContext().put("pages", userService.findUserByDep(depId));
		return SUCCESS;
	}
	
	public String addInput() {
		ActionContext.getContext().put("ds", departmentService.listAllDep());
		return SUCCESS;
	}
	
	public String add() {
		userService.add(user, depId);
		ActionUtil.setUrl("/user_list");
		return ActionUtil.REDIRECT;
	}
	
	public String updateInput() throws IllegalAccessException, InvocationTargetException {
		User tu = userService.load(user.getId());
		BeanUtils.copyProperties(user, tu);
		ActionContext.getContext().put("ds",departmentService.listAllDep());
		return SUCCESS;
	}
	
	public String update() throws IllegalAccessException, InvocationTargetException {
		User tu = userService.load(user.getId());
		tu.setEmail(user.getEmail());
		tu.setNickname(user.getNickname());
		tu.setType(user.getType());
		userService.update(tu, depId);
		ActionUtil.setUrl("/user_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public String show() throws IllegalAccessException, InvocationTargetException {
		User tu = userService.load(user.getId());
		BeanUtils.copyProperties(user, tu);
		return SUCCESS;
	}
	
	public String delete() {
		userService.delete(user.getId());
		ActionUtil.setUrl("/user_list");
		return ActionUtil.REDIRECT;
	}
	
	public String updateSelfInput() throws IllegalAccessException, InvocationTargetException {
		User tu = (User)ActionContext.getContext().getSession().get("loginUser");
		BeanUtils.copyProperties(user, tu);
		return SUCCESS;
	}
	
	public String updateSelf() throws IllegalAccessException, InvocationTargetException {
		User tu = userService.load(user.getId());
		tu.setEmail(user.getEmail());
		tu.setNickname(user.getNickname());
		userService.update(tu);
		ActionContext.getContext().getSession().put("loginUser",tu);
		ActionUtil.setUrl("/user_showSelf.action");
		return ActionUtil.REDIRECT;
	}
	
	public String showSelf() throws IllegalAccessException, InvocationTargetException {
		User tu = (User)ActionContext.getContext().getSession().get("loginUser");
		BeanUtils.copyProperties(user, tu);
		return SUCCESS;
	}
	
	public void validateAdd() {
		if(ActionUtil.isEmpty(user.getUsername())) {
			this.addFieldError("username","用户名称不能为空");
		}
		if(this.hasFieldErrors()){
			addInput();
		}
	}
	@Override
	public User getModel() {
		if(user == null)
			user = new User();
		return user;
	}
	
}
