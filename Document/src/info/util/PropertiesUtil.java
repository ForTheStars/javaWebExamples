package info.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties autoProp;
	
	public static Properties getAuthProp() {
		try {
			if(autoProp == null){
				autoProp = new Properties();
				autoProp.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("auth.properties"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return autoProp;
	}
}
