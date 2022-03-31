package cn.tedu.config.filter;

import cn.tedu.config.filter.request.MyParamsWraper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//多种参考
//使用@WebFilter注解向SpringBoot中添加Filter
//urlPatterns ： 要过滤的请求路径
@WebFilter(filterName = "paramsFilter", urlPatterns = "/params")
public class ParamsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //新建一个自己定义的HttpServletRequestWrapper对象，将当前的servletRequest传进去
        MyParamsWraper requestParamsWrapper = new MyParamsWraper((HttpServletRequest) servletRequest);
        //添加参数
        requestParamsWrapper.addParameter("param1", "1111");
        requestParamsWrapper.addParameter("param2", "2222");
        //执行Filter，这里传入的Reqeust为刚才新建的自定义的HttpServletRequestWrapper对象
        filterChain.doFilter(requestParamsWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
