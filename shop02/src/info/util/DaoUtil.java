package info.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import info.dao.IFactoryDao;
import info.model.ShopDi;

public class DaoUtil {

	public static void main(String[] args) {
		System.out.println(createDaoFactory());
	}
	public static void diDao(Object obj){
		try {
			Method[] ms = obj.getClass().getDeclaredMethods();
			for(Method m:ms){
				if(m.isAnnotationPresent(ShopDi.class)){
					ShopDi sd = m.getAnnotation(ShopDi.class);
					String mn = sd.value();
					if(mn == null || "".equals(mn.trim())){
						mn = m.getName().substring(3);
						mn = mn.substring(0, 1).toLowerCase()+mn.substring(1);
					}
					Object o = DaoUtil.createDaoFactory().getDao(mn);
					m.invoke(obj,o);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public static IFactoryDao createDaoFactory(){
		IFactoryDao factoryDao = null;
		try {
			Properties properties = PropertiesUtil.getDaoProp();
			String fs = properties.getProperty("factory");
			Class clz = Class.forName(fs);
			String mn = "getInstance";
			Method method = clz.getMethod(mn);
			factoryDao = (IFactoryDao)method.invoke(clz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return factoryDao;
	}
}
