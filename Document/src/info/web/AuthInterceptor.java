package info.web;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import info.exception.DocumentException;
import info.model.User;
import info.util.ActionUtil;

@Component("authInterceptor")
public class AuthInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//获取action的名称
		String an = invocation.getProxy().getActionName();
		if(!an.startsWith("login")) {
			User loginUser = (User)ActionContext.getContext().getSession().get("loginUser");
			if(loginUser == null) {
				return "login";
			}
			if(loginUser.getType() != 1) {
				if(!ActionUtil.checkUrl(an)) {
					throw new DocumentException("需要管理员才能访问该功能");
				}
			}
		}
		return invocation.invoke();
	}

}
