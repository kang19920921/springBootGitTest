package cn.tedu.config.filter;

import cn.tedu.Utils.UUIDUtils;
import cn.tedu.config.filter.request.OtWrapperedRequest;
import lombok.extern.slf4j.Slf4j;
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
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String uri = httpServletRequest.getRequestURI();
            log.info("====== ot logbackFilter.uri  ===== < {} >", uri);
            String wtraceid = UUIDUtils.getUUID16();
            MDC.put("wtraceid", wtraceid);
            OtWrapperedRequest  wrapperedRequest = new OtWrapperedRequest(httpServletRequest);
            wrapperedRequest.addParameter("wtraceid",wtraceid);

            filterChain.doFilter(servletRequest,servletResponse);

        } finally {
            MDC.remove("wtraceid");
        }

    }

    @Override
    public void destroy() {

    }
}
