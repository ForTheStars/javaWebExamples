package info.util;

import java.util.Properties;

import com.opensymphony.xwork2.ActionContext;

public class ActionUtil {
	public final static String REDIRECT = "redirect";
	
	public static boolean isEmpty(String str) {
		if(str==null || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	public static void setUrl(String url) {
		ActionContext.getContext().put("url", url);
	}
	
	public static String[] getUserAuth() {
		Properties properties = PropertiesUtil.getAuthProp();
		String user = properties.getProperty("user");
		return user.split(",");
	}
	
	public static String[] getUserNotAuth() {
		Properties properties = PropertiesUtil.getAuthProp();
		return properties.getProperty("admin").split(",");
	}
	
	public static boolean checkUrl(String action) {
		for(String url:getUserAuth()) {
			if(action.startsWith(url)){
				return true;
			}
		}
		for(String url:getUserNotAuth()){
			if(action.startsWith(url)){
				return false;
			}
		}
		return true;
	}
}
