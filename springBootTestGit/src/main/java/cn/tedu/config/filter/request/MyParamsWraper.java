package cn.tedu.config.filter.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 多种方法参考
 */
public class MyParamsWraper extends HttpServletRequestWrapper {
    //这里map需要指定为 Map<String, String[]> 类型
    private Map<String, String[]> params = new HashMap<>();

    /**
     * 构造方法，将原有请求中的参数复制到当前类的params中
     * @param request
     */
    public MyParamsWraper(HttpServletRequest request) {
        super(request);
        params.putAll(request.getParameterMap());
    }


    public void addParameter(String key, Object value) {
        if(value != null) {
            this.params.put(key, new String[] {String.valueOf(value)});
        }
    }

    /**
     * 如果在SpringBoot中用对象来接收参数，这个方法就必须重写
     * @return
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector(this.params.keySet()).elements();
    }

    /**
     * 这个方法必须重写
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = this.params.get(name);
        if((values == null) || (values.length == 0)) {
            return null;
        }
        return values;
    }
}
