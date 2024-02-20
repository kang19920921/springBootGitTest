package cn.tedu.config.filter;

import cn.tedu.Utils.HttpRequestUtils;
import cn.tedu.Utils.UUIDUtils;

import cn.tedu.Utils.ValidateUtil;
import cn.tedu.config.filter.request.OtWrapperedRequestBackkup;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
@Component
public class OtLogbackFilter  implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        try {
//            ValidateUtil.assertNotNullWarm(null,"测试一下异常2");
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String uri = httpServletRequest.getRequestURI();
            log.info("====== ot logbackFilter.uri  ===== < {} >", uri);
            //获取请求数据
            JSONObject reqJsonObject = HttpRequestUtils.getParamsJsonObj(httpServletRequest); // 第一个处理基类参数的过滤器获取参数时根据请求方法和contentType取值

            String wtraceid = reqJsonObject.getString("wtraceid");
            wtraceid = StringUtils.isBlank(wtraceid) ? UUIDUtils.getUUID16() : wtraceid;
            reqJsonObject.put("wtraceid", wtraceid);
            MDC.put("wtraceid", wtraceid);

            OtWrapperedRequestBackkup  wrapperedRequest = HttpRequestUtils.freshParams(httpServletRequest,reqJsonObject);

            filterChain.doFilter(wrapperedRequest,servletResponse);

        } finally {
            MDC.remove("wtraceid");
        }

    }

    @Override
    public void destroy() {

    }
}
