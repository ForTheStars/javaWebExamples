package info.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import info.model.SystemContext;

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
			} catch (NumberFormatException e) {
			}
			SystemContext.setPageOffset(pageOffset);
			SystemContext.setPageSize(pageSize);
			filterChain.doFilter(request, response);
		} finally {
			SystemContext.removePageOffset();
			SystemContext.removePageSize();
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
