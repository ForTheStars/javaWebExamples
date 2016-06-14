package info.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.junit.Test;

import info.dao.IFactoryDao;
import info.model.ShopDi;
import info.util.DaoUtil;
import info.util.PropertiesUtil;

public class TestAnnotation {
	
/*	@ShopDi("addressDao")
	public void aa(){
		
	}*/
	
	@ShopDi
	public void setUserDao(){

	}
	
	@ShopDi
	public void setCategoryDao(){
		
	}
	
	@ShopDi("categoryDao")
	public void sdas(){
		
	}
	
	@Test
	public void test01() {
		Method[] methods = this.getClass().getDeclaredMethods();
		for(Method m:methods){
			System.out.println(m.getName()+":"+m.isAnnotationPresent(ShopDi.class));
			if(m.isAnnotationPresent(ShopDi.class)){
				ShopDi sd = m.getAnnotation(ShopDi.class);
				String v = sd.value();
				if(v == null || "".equals(v.trim())){
					v = m.getName().substring(3);
					v = v.substring(0, 1).toLowerCase()+v.substring(1);
				}
				System.out.println(v);
				Object o = DaoUtil.createDaoFactory().getDao(v);
				System.out.println(o);
			}
		}
	}
}
