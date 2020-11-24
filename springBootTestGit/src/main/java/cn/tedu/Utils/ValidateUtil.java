package cn.tedu.Utils;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class ValidateUtil {

	public ValidateUtil() {
		// TODO Auto-generated constructor stub
	}

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