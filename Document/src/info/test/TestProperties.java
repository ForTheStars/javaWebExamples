package info.test;

import java.util.Properties;

import org.junit.Test;
import info.util.ActionUtil;
import info.util.PropertiesUtil;

public class TestProperties {

	@Test
	public void testPropRead() {
		Properties prop = PropertiesUtil.getAuthProp();
		System.out.println(prop.get("admin"));
	}
	
	@Test
	public void testAuth() {
		System.out.println(ActionUtil.getUserAuth()[0]);
		System.out.println(ActionUtil.getUserNotAuth()[1]);
	}
}
