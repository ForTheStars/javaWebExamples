package info.util;

import info.model.ValidateForm;
import info.model.ValidateType;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.sun.org.glassfish.external.statistics.Statistic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class RequestUtil {
	public final static String[] allowFile={"jpg","bmp","gif","png"};
	public final static String PATH = "D:\\GitHub_code\\javaWebExamples\\shop02\\WebContent";
	
	@SuppressWarnings("unchecked")
	public static void uploadFile(String fname,String filedName,byte[] fs,HttpServletRequest req)throws FileNotFoundException,IOException{
		FileOutputStream fos = null;
		try {
			if(fs.length > 0){
				String fn = FilenameUtils.getName(fname);
				String ext = FilenameUtils.getExtension(fname);
				System.out.println(ext);
				boolean b = checkFile(ext);
				if(b){
					fos = new FileOutputStream(PATH+"/img/"+fn);
					fos.write(fs,0,fs.length);
				}else{
					Map<String, String> errors = (Map<String, String>)req.getAttribute("errors");
					errors.put(filedName, "图片类型必须是jpg,bmp,png,gif");
				}
			}
		} finally {
			if(fos != null) fos.close();
		}
 	}
	
	private static boolean checkFile(String ext){
		for(String s:allowFile){
			if(ext.equals(s)){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean validate(Class<?> clz,HttpServletRequest req){
		Field[] fs = clz.getDeclaredFields();
		boolean isValidate = true;
		Map<String,String> errors = (Map<String,String>)req.getAttribute("errors");
		for(Field f:fs){
			if(f.isAnnotationPresent(ValidateForm.class)){
				ValidateForm vf = f.getAnnotation(ValidateForm.class);
				ValidateType vt = vf.type();
				if(vt == ValidateType.NotNull){
					//TODO
					boolean b = validateNotNull(f.getName(), req);
					if(!b){
						isValidate = false;
						errors.put(f.getName(), vf.errorMsg());
					}
				}else if(vt == ValidateType.Length){
					boolean b = validateLength(f.getName(), req, vf.value());
					if(!b){
						isValidate = false;
						errors.put(f.getName(), vf.errorMsg());
					}
				}else if(vt == ValidateType.Number){
					boolean b = validateNumber(f.getName(), req);
					if(!b){
						isValidate = false;
						errors.put(f.getName(), vf.errorMsg());
					}
				}
			}
		}
		return isValidate;
	}
	
	private static boolean validateNumber(String name,HttpServletRequest req){
		try {
			Double.parseDouble(req.getParameter(name));
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private static boolean validateLength(String name,HttpServletRequest req,String value){
		String v = req.getParameter(name);
		if(v == null || "".equals(v.trim())){
			return false;
		}
		if(v.length() < 6){
			return false;
		}
		return true;
	}
	
	private static boolean validateNotNull(String name,HttpServletRequest req){
		if(!req.getParameterMap().containsKey(name)){
			return true;
		}
		String v = req.getParameter(name);
		if(v == null || "".equals(v.trim())){
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static Object setParam(Class<?> clz,HttpServletRequest req){
		Map<String, String[]> params = req.getParameterMap();
		Set<String> keys = params.keySet();
		Object object = null;
		try {
			object = clz.newInstance();
			for(String key:keys){
				String[] vv = params.get(key);
				if(vv.length > 1){
					BeanUtils.copyProperty(object, key, vv);
				} else {
					BeanUtils.copyProperty(object, key, vv[0]);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return object;
	}
}
