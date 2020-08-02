package cn.tedu.Utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

	private ListUtils() {}
		
	public static <T> List<T> limitArray(List<T> list, int maxsize) {
		List<T> newList = new ArrayList<T>(maxsize);
		for (T t : list) {
			if (!newList.contains(t)) {
				newList.add(t);
			}

			if (newList.size() >= maxsize) {
				break;
			}

		}

		return newList;

	}
	
	
}
