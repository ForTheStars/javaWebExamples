package info.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import info.util.PropertiesUtil;
import jdk.internal.dynalink.beans.StaticClass;

public class PropertiesFactory implements IFactoryDao{
	private static PropertiesFactory factory  = new PropertiesFactory();
	private static Map<String, Object> daos = new HashMap<>();
	private PropertiesFactory(){};
	public static IFactoryDao getInstance(){
		return factory;
	}
	@Override
	public Object getDao(String name) {
		try {
			if(daos.containsKey(name)){
				return daos.get(name);
			}
			Properties properties = PropertiesUtil.getDaoProp();
			String cn = properties.getProperty(name);
			Object object = Class.forName(cn).newInstance();
			daos.put(name, object);
			return object;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
