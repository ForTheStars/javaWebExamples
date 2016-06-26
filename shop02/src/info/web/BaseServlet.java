package info.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import info.model.User;
import info.util.DaoUtil;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = -2679836167977791174L;
	public final static String redirPath="redirct:";
	private Map<String, String> errors = new HashMap<>();
	protected String redirPath(String path){
		return redirPath+path;
	}
	
	protected Map<String, String> getErrors(){
		return errors;
	}
	
	protected boolean hasErrors(){
		if(errors != null && errors.size() > 0){
			return true;
		}
		return false;
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			errors.clear();
			req.setAttribute("errors", errors);
			if(ServletFileUpload.isMultipartContent(req)){
				req = new MultipartWrapper(req);
			}
			DaoUtil.diDao(this);
			String method = req.getParameter("method");
			System.out.println("-----------------"+method+"--------------");
			Method m = this.getClass().getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			int flag = checkAuth(req, resp, m);
			if(flag == 1){
				resp.sendRedirect("user.do?method=loginInput");
				return;
			} else if(flag == 2){
				req.setAttribute("errorMsg", "你没有权限访问改功能");
				req.getRequestDispatcher("/WEB-INF/inc/error.jsp").forward(req, resp);
			}
			String path = (String)m.invoke(this,req,resp);
			if(path.startsWith(redirPath)){
				String rp = path.substring(redirPath.length());
				resp.sendRedirect(rp);
			} else {
				req.getRequestDispatcher("/WEB-INF/"+path).forward(req, resp);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private int checkAuth(HttpServletRequest req,HttpServletResponse resp,Method m){
		User lu = (User)req.getSession().getAttribute("loginUser");
		if(lu != null && lu.getType() == 1){
			return 0;
		}
		if(!m.isAnnotationPresent(Auth.class)){
			if(lu == null){
				return 1;
			}else if(lu.getType() != 1){
				return 2;
			}
		} else {
			Auth auth = m.getAnnotation(Auth.class);
			String v = auth.value();
			if(v.equals("any")){
				return 0;
			} else if(v.equals("user")){
				if(lu == null){
					return 1;
				}else{
					return 0;
				}
			}
		}
		return 0;
	}
}
