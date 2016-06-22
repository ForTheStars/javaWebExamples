package info.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import info.model.User;

public class ValidateUtil {
	public static boolean validateNull(HttpServletRequest request,String[] fields){
		boolean validate = true;
		Map<String, String> errorMsg = new HashMap<>();
		for(String field:fields){
			String value = request.getParameter(field);
			if(value == null || "".equals(value.trim())){
				validate = false;
				errorMsg.put(field, field+"不能为空");
			}
		}
		if(!validate) request.setAttribute("errorMsg", errorMsg);
		return validate;
	}
	
	public static String showError(HttpServletRequest request,String field){
		Map<String, String> errorMsg = (Map<String, String>)request.getAttribute("errorMsg");
		if(errorMsg == null) return "";
		String msg = errorMsg.get(field);
		if(msg == null) return "";
		return msg;
	}
	
	public static boolean checkAdminOrSelf(HttpSession session,int userId) {
		User u = (User)session.getAttribute("loginUser");
		if(u==null) return false;
		if(u.getType()==1) return true;
		if(u.getId()==userId) return true;
		return false;
	}
}
