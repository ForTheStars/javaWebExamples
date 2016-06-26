package info.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.dao.IUserDao;
import info.model.Pager;
import info.model.ShopDi;
import info.model.ShopException;
import info.model.User;
import info.util.RequestUtil;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 6554520321884097292L;
	private IUserDao userDao;
	
	@ShopDi
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public String list(HttpServletRequest request,HttpServletResponse response){
		Pager<User> users = userDao.find("");
		request.setAttribute("users", users);
		return "user/list.jsp";
	}
	
	@Auth("any")
	public String addInput(HttpServletRequest request,HttpServletResponse response){
		return "user/addInput.jsp";
	}
	
	public String delete(HttpServletRequest request,HttpServletResponse response){
		int id =Integer.parseInt(request.getParameter("id"));
		userDao.delete(id);
		return redirPath+"user.do?method=list";
	}
	
	public String updateInput(HttpServletRequest request,HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		User user = userDao.load(id);
		request.setAttribute("user", user);
		return "user/updateInput.jsp";
	}
	
	public String changeType(HttpServletRequest request,HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("id"));
		User user = userDao.load(id);
		if(user.getType() == 0){
			user.setType(1);
		} else {
			user.setType(0);
		}
		userDao.update(user);
		return redirPath+"user.do?method=list";
	}
	
	public String update(HttpServletRequest request,HttpServletResponse response){
		User tu = (User)RequestUtil.setParam(User.class, request);
		boolean isValidate = RequestUtil.validate(User.class, request);
		int id = Integer.parseInt(request.getParameter("id"));
		User user = userDao.load(id);
		user.setNickname(tu.getNickname());
		if(!isValidate){
			request.setAttribute("user", user);
			return "user/updateInput.jsp";
		}
		user.setPassword(tu.getPassword());
		user.setNickname(tu.getNickname());
		userDao.update(user);
		return redirPath+"user.do?method=list";
	}
	@Auth("any")
	public String add(HttpServletRequest request,HttpServletResponse response){
		User user = (User)RequestUtil.setParam(User.class, request);
		boolean isValidate = RequestUtil.validate(User.class, request);
		if(!isValidate){
			return "user/addInput.jsp";
		}
		try {
			userDao.add(user);
		} catch (ShopException e) {
			request.setAttribute("errorMsg", e.getMessage());
			return "inc/error.jsp";
		}
		return redirPath("user.do?method=list");
	}
	@Auth("any")
	public String loginInput(HttpServletRequest request,HttpServletResponse response){
		return "user/loginInput.jsp";
	}
	@Auth("any")
	public String login(HttpServletRequest request,HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = userDao.login(username, password);
			request.getSession().setAttribute("loginUser", user);
		} catch (ShopException e) {
			request.setAttribute("errorMsg", e.getMessage());
			return "inc/error.jsp";
		}
		return redirPath("user.do?method=list");
	}
	@Auth("any")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().invalidate();
		return redirPath("product.do?method=list");
	}
	
	@Auth
	public String updateSelfInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("user", (User)req.getSession().getAttribute("loginUser"));
		return "user/updateSelfInput.jsp";
	}
	
	@Auth
	public String updateSelf(HttpServletRequest request,HttpServletResponse response){
		User tu = (User)RequestUtil.setParam(User.class, request);
		boolean isValidate = RequestUtil.validate(User.class, request);
		User user = (User)request.getSession().getAttribute("loginUser");
		user.setPassword(tu.getPassword());
		user.setNickname(tu.getNickname());
		if(!isValidate){
			request.setAttribute("user", user);
			return "user/updateSelfInput.jsp";
		}
		userDao.update(user);
		return redirPath("goos.do?method=list");
	}
	@Auth
	public String show(HttpServletRequest request,HttpServletResponse response){
		User user = userDao.load(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("user", user);
		return "user/show.jsp";
	}
}

