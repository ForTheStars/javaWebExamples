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
	
}
