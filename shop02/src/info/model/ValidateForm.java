package info.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateForm {
	/*
	 * type表示验证的类型1表示不能为空，2表示长度，3表示邮件，4表示数字
	 */
	public ValidateType type();
	
	public String errorMsg();
	
	public String value() default "";
}
