package info.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用这个Annotation来标注需要进行依赖注入的方法
 * @ShopDi("userDao")就说明该方法是注入userDao对象
 * 如果@ShopDi就规定该方法使用setXX来注入，如果方法是setUserDao表示注入userDao
 * @author Administrator
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ShopDi {
	/*
	 * 表示为这个Annotation加上了一个属性值，如果没有定义default，必须在该annotation中
	 * 定义这个属性
	 * @ShopDi(abc="ss")
	 */
//	String abc() default "";
	/*
	 * value是Annotation的默认属性，在定义的时候可以不用value=""定义，
	 * 而直接通过@shopDi("hello")
	 * 特别注意：当需要定义两个以上的属性时，默认属性就不起作用了
	 * @ShopDi(value="hello",abc="ok")
	 */
	String value() default "";
}
