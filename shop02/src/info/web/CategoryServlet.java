package info.web;

import info.dao.ICategoryDao;
import info.model.Category;
import info.model.ShopDi;
import info.model.ShopException;
import info.util.RequestUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CategoryServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private ICategoryDao categoryDao;

	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}
	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public String list(HttpServletRequest req,HttpServletResponse resp) {
		String name = req.getParameter("name");
		List<Category> list = categoryDao.list(name);
		req.setAttribute("cs",list);
		return "category/list.jsp";
	}
	
	public String addInput(HttpServletRequest req,HttpServletResponse resp) {
		return "category/addInput.jsp";
	}
	
	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("category",
				categoryDao.load(Integer.parseInt(req.getParameter("id"))));
		return "category/updateInput.jsp";
	}
	
	public String update(HttpServletRequest req,HttpServletResponse resp) {
		Category category = categoryDao.load(Integer.parseInt(req.getParameter("id")));
		Category tc = (Category)RequestUtil.setParam(Category.class, req);
		category.setName(tc.getName());
		if(!RequestUtil.validate(Category.class, req)) {
			return "category/updateInput.jsp";
		}
		categoryDao.update(category);
		return redirPath("category.do?method=show&id="+category.getId());
	}
	
	public String add(HttpServletRequest req,HttpServletResponse resp) {
		Category category = (Category)RequestUtil.setParam(Category.class, req);
		if(!RequestUtil.validate(Category.class, req)) {
			return "category/addInput.jsp";
		}
		categoryDao.add(category);
		return redirPath("category.do?method=list");
	}
	
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		Category category = categoryDao.load(Integer.parseInt(req.getParameter("id")));
		req.setAttribute("category",category);
		return "category/show.jsp";
	}
	
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		try {
			categoryDao.delete(Integer.parseInt(req.getParameter("id")));
		} catch (ShopException e) {
			req.setAttribute("errorMsg", e.getMessage());
			return "inc/error.jsp";
		}
		return redirPath("category.do?method=list");
	}
	
	
}
