package info.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import info.model.SystemContext;
import info.model.User;

public class SystemContextFilter implements Filter {

	private int pageSize = 0;
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			int pageOffset = 0;
			try {
				pageOffset = Integer.parseInt(request.getParameter("pager.offset"));
			} catch (NumberFormatException e) {}
			HttpServletRequest hRequest = (HttpServletRequest)request;
			SystemContext.setPageOffset(pageOffset);
			SystemContext.setPageSize(pageSize);
			
			String realPath = hRequest.getSession().getServletContext().getRealPath("");  //由于STS中获取的是发布空间的路径，所以暂时用以下绝对路径代替
			realPath = "D:\\GitHub_code\\javaWebExamples\\Document\\WebContent";
			SystemContext.setRealPath(realPath);
			User loginUser = (User)hRequest.getSession().getAttribute("loginUser");
			if(loginUser != null){
				SystemContext.setLoginUser(loginUser);
			}
			filterChain.doFilter(request, response);
		} finally {
			SystemContext.removePageOffset();
			SystemContext.removePageSize();
			SystemContext.removeLoginUser();
			SystemContext.removeRealPath();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			pageSize = Integer.parseInt(filterConfig.getInitParameter("pageSize"));
		} catch (NumberFormatException e) {
			pageSize = 10;
		}
	}

}
