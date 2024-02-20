package cn.tedu.config.filter;


import cn.tedu.Utils.HttpRequestUtils;
import cn.tedu.enums.ProcessCodeEnum;
import cn.tedu.exception.ProcessException;
import cn.tedu.pojo.ResultVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
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
public class OTExceptionFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                try{
                    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                    String uri = httpServletRequest.getRequestURI();
                    log.info("========= ot Exception do filter.uri ==== <{}> ",uri);
                    chain.doFilter(request,response);
                }catch (Exception e){
                    handlerException( response, e);
                }
    }


    private void handlerException( ServletResponse response,Exception e) throws IOException {
        log.info("Exception handlerException .....");

        ResultVO<Object> resultVO = null;
        if(e instanceof ProcessException ){
            log.warn(e.getMessage(),e);
            ProcessException p = (ProcessException) e;
            resultVO = new ResultVO<>(p.getErrorCode(),e.getMessage());
        }else{
            log.error(e.getMessage(),e);
            resultVO = ProcessCodeEnum.FAIL.buildFailResultVO("未知异常,请联系管理员");
        }
        String resultVOJson = JSONObject.toJSONString(resultVO);
        log.info("exceptionFilter handlerException resultVOJson= {}",resultVOJson);
        HttpRequestUtils.writeResponse(response,resultVOJson);
    }





    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
