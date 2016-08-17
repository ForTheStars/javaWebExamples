package info.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.dao.IAddressDao;
import info.dao.IUserDao;
import info.model.Address;
import info.model.ShopDi;
import info.model.User;
import info.util.RequestUtil;

public class AddressServlet extends BaseServlet {

	private static final long serialVersionUID = -2886702111078872446L;
	private IUserDao userDao;
	private IAddressDao addressDao;
	
	@ShopDi
	public void setAddressDao(IAddressDao addressDao){
		this.addressDao = addressDao;
	}
	@ShopDi
	public void setUserDao(IUserDao userDao){
		this.userDao = userDao;
	}
	
	@Auth
	public String add(HttpServletRequest request,HttpServletResponse response){
		User user = userDao.load(Integer.parseInt(request.getParameter("userId")));
		Address address = (Address)RequestUtil.setParam(Address.class, request);
		if(!RequestUtil.validate(Address.class, request)){
			request.setAttribute("user", user);
			return "address/addInput.jsp";
		}
		addressDao.add(address, user.getId());
		return redirPath(request.getContextPath()+"/user.do?method=show&id="+user.getId());
	}
	
	@Auth
	public String delete(HttpServletRequest request,HttpServletResponse response){
		int addressId = Integer.parseInt(request.getParameter("id"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		addressDao.delete(addressId);
		return redirPath(request.getContextPath()+"/user.do?method=show&id="+userId);
	}
	
	@Auth
	public String addInput(HttpServletRequest request,HttpServletResponse response){
		User user = userDao.load(Integer.parseInt(request.getParameter("userId")));
		request.setAttribute("user", user);
		return "address/addInput.jsp";
	}
	@Auth
	public String updateInput(HttpServletRequest request,HttpServletResponse response){
		Address address = addressDao.load(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("address", address);
		return "address/updateInput.jsp";
	}
	@Auth
	public String update(HttpServletRequest request,HttpServletResponse response){
		Address address = addressDao.load(Integer.parseInt(request.getParameter("id")));
		Address ta = (Address)RequestUtil.setParam(Address.class, request);
		address.setName(ta.getName());
		address.setPhone(ta.getPhone());
		address.setPostcode(ta.getPostcode());
		if(!RequestUtil.validate(Address.class, request)){
			request.setAttribute("address", address);
			return "address/updateInput.jsp";
		}
		addressDao.update(address);
		return redirPath("user.do?method=show&id="+address.getUser().getId());
	}
}
