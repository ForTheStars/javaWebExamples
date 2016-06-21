package info.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
}
