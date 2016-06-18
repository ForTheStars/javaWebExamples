package info.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import info.model.SystemContext;

public class SystemContextFilter implements Filter {
	private int pageSize;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			int pageIndex = 1;
			try {
				pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			} catch (NumberFormatException e) {
			}
			SystemContext.setPageIndex(pageIndex);
			SystemContext.setPageSize(pageSize);
			filterChain.doFilter(request, response);
		} finally {
			SystemContext.removePageIndex();
			SystemContext.removePageSize();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			pageSize = Integer.parseInt(filterConfig.getInitParameter("pageSize"));
		} catch (NumberFormatException e) {
			pageSize = 15;
		}
	}

}
