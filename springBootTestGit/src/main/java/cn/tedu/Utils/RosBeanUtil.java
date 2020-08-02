package cn.tedu.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Captkang
 * @date: 2020年7月26日上午10:29:54
 */
@Slf4j
public class RosBeanUtil {

	private RosBeanUtil() {
	}

	/**
	 * @author Captkang 2020/07/06
	 * @param srcDTO
	 * @param clazz
	 * @return
	 */
	public static <T> T copyEntry(Object srcDTO, Class<T> clazz) {
		try {
			if (null == srcDTO) {
				return clazz.newInstance();
			}
			if(srcDTO instanceof String) {
				String str = (String) srcDTO;
				return JSON.parseObject(str,clazz);
			}else {			
				return JSON.parseObject(JSON.toJSONString(srcDTO), clazz);
			}

		} catch (Exception e) {
			log.error("拷贝Entry属性未知异常", e);
			throw new RuntimeException();
		}
	}

	/**
	 * @author Captkang 2020/07/06
	 * @param srcDTO
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> copyList(List<?> srcLsit, Class<T> clazz) {
		try {
			if (CollectionUtils.isEmpty(srcLsit)) {
				return new ArrayList<T>();
			}
			return JSON.parseArray(JSON.toJSONString(srcLsit), clazz);

		} catch (Exception e) {
			log.error("拷贝Entry属性未知异常", e);
			throw new RuntimeException();
		}
	}

	private <T> T getListFisrt(List<T> data) {
		if (CollectionUtils.isEmpty(data)) {
			return null;
		}
		return (T) data;
	}

	/**
	 * @author Captkang
	 * @t
	 * @param srcObject
	 * @return
	 */
	public static <T> T deepClone(T srcObject) {

		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(srcObject);

			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			@SuppressWarnings("unchecked")
			T cloneObj = (T) ois.readObject();
			return cloneObj;

		} catch (Exception e) {
			throw new RuntimeException("深克隆未知异常", e);
		} finally {
			close(ois,bis,oos,bos);
		}

	}

	/**
	 * 
	 * @author Captkang
	 * @date: 2020年7月26日上午10:30:43
	 * @param ios
	 */
	private static void close(Closeable... ios) {
		for (Closeable io : ios) {
			try {
				if (io != null) {
					io.close();
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

	}

}