package cn.tedu.Utils;


import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
		
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
		
	/**
	 * 对象转化为json
	 */
	public static String toJSON(Object object) {
		
		String result = null ;
				
		try {
			result  = MAPPER.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 
		
		return result;
		
	}
	
	public static <T> T  toObject(String json ,Class<T> targetClass){
				
		T object = null;
		try {			
			object = MAPPER.readValue(json, targetClass);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
				
		return object;		
	}
	
	
	
}