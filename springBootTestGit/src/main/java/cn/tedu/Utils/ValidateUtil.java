package cn.tedu.Utils;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cn.tedu.enums.ProcessCodeEnum;

public class ValidateUtil {

	public ValidateUtil() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	/**
	 *  断言参数不为空,为空则抛出A999-参数异常 
	 *  1.入参对象为String,断言非blank;
	 *  2.入参对象类型Collection,断言非empty
	 *  3.入参对象类型为Map ,断言非empty
	 * 
	 * @author Captkang
	 * @date: 2020年11月29日上午11:00:35
	 * @param obj
	 * @param errMsg
	 */
	public static void assertNotNullWarm(Object obj ,String errMsg) {
		assertNotNull(obj, errMsg, ProcessCodeEnum.PARM_WARM);		
	}
	
	
	/**
	 * 断言参数为空，不为空则抛出A999异常
	 *  1.入参对象为String,断言blank;
	 *  2.入参对象类型Collection,断言empty
	 *  3.入参对象类型为Map ,断言empty
	 * 
	 * @author Captkang
	 * @date: 2020年11月29日上午11:06:11
	 * @param obj
	 * @param errMsg
	 */
	public static void assertNullWarm(Object obj,String errMsg) {
		assertNull(obj,errMsg,ProcessCodeEnum.PARM_WARM);
	}
	
	
	
	
	/**
	 * 断言为true,不为空则抛出A999-参数异常
	 * 
	 * @author Captkang
	 * @date: 2020年11月29日上午10:40:16
	 * @param flag
	 * @param errMsg
	 */
	public static void assertTrueWarm(boolean flag,String errMsg) {
		assertTrue(flag, errMsg, ProcessCodeEnum.PARM_WARM);
	}
	
		
	/**
	 * 断言为false, 不为则抛出P999-参数异常
	 * 
	 * @author Captkang
	 * @date: 2020年11月28日下午5:48:16
	 * @param flag
	 * @param errMsg
	 */
	public static void assertFalseWarn(boolean flag,String errMsg) {
		assertTrue(flag, errMsg, ProcessCodeEnum.PARM_WARM);
	}
	
	
	/**
	 * 断言参数不为空,为空则抛出异常
	 * 
	 * @author Captkang
	 * @date: 2020年11月28日下午5:32:56
	 * @param obj
	 * @param errMsg
	 * @param processCodeEnum
	 */
	private static void assertNotNull(Object obj,String errMsg,ProcessCodeEnum processCodeEnum) {
		if(!isNull(obj)) {
			throw processCodeEnum.buildException(errMsg);
		}
	}	
	
	
	
	
	/**
	 * 断言参数为空,不为空则抛出异常
	 * 
	 * @author Captkang
	 * @date: 2020年11月28日下午5:32:56
	 * @param obj
	 * @param errMsg
	 * @param processCodeEnum
	 */
	private static void assertNull(Object obj,String errMsg,ProcessCodeEnum processCodeEnum) {
		if(!isNull(obj)) {
			throw processCodeEnum.buildException(errMsg);
		}
		
	}
	
		
	/**
	 * 断言参数为True，不为则抛出异常
	 * 
	 * @author Captkang
	 * @date: 2020年11月28日下午5:18:20
	 * @param flag
	 * @param errMsg
	 * @param processCodeEnum
	 */
    private static void assertTrue(boolean flag,String errMsg,ProcessCodeEnum processCodeEnum) {
    	if(!flag) {
    		throw processCodeEnum.buildException(errMsg);
    	}   	
    }
	
	

	/**
	 * 断言参数为false, 不为则抛出异常
	 * 
	 * @author Captkang
	 * @date: 2020年11月28日下午5:15:02
	 * @param flag
	 * @param errMsg
	 * @param processCodeEnum
	 */
	public static void assertFalse(boolean flag,String errMsg,ProcessCodeEnum processCodeEnum) {
		if(flag) {
			throw processCodeEnum.buildException(errMsg);
		}
	}
	
		
	/**
	 * 断言参数为空
	 * 1.入参对象类型为String,断言为blank
	 * 2.入参对象类型Collection,断言为empty
	 * 3.入参对象类型为map,断言为empty
	 * 
	 * @author Captkang
	 * @date: 2020年11月28日下午5:07:30
	 * @param obj
	 * @return
	 */
	private static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			return StringUtils.isNotBlank(str);
		} else if (obj instanceof Collection) {
			Collection<?> coll = (Collection<?>) obj;
			return CollectionUtils.isEmpty(coll);
		} else if (obj instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) obj;
			return map.isEmpty();
		}
		return false;
	}
}