package cn.tedu.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * 
 * @author Captkang
 * @date: 2020年9月6日下午5:22:34
 */
@Retention(RUNTIME)//存活阶段
@Target(FIELD)//注解可用的地方  (字段、方法 )
public @interface RosExcel {
	
	
	/**
	 * 导出到excel中的列标题名称
	 * 
	 */
	public abstract String name();
	
	
	
	/**
	 * EXCEL第几列  A B C D 
	 */
	
	public abstract String  column();
	
	
	
	/**
	 * 单元格宽度
	 *  
	 */
 	public abstract int width() default 4000;
 	
 	
 	/**
 	 * 水平方向对齐方式 1-左对齐 0-居中 1-右对齐
 	 * 
 	 */ 
	public abstract HorizontalAlignment horizontal() default HorizontalAlignment.CENTER;
	
	
	
	/***
	 * 设置只能选择不能输入的列内容
	 * 
	 */
	public abstract String [] combo() default {};
	
	
	/**
	 * 是否导出字段的值 false-不导出
	 * 
	 */
	public abstract boolean isEXport() default true ;
	
	
	/**
	 * 提示信息
	 * 
	 */
	public abstract String prompt() default "";
	
	
	/***
	 * 是否设置整列为文本格式: true- 设置为文本格式
	 * 
	 */
	public abstract boolean isText() default false;
	
}
