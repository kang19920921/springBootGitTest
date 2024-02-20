package cn.tedu.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class AopLogUtil {

    private long beginTime;
    private long endTime;
    private String uri;

    public void before(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        try {
            beginTime = System.currentTimeMillis();
            uri = request.getRequestURI();
            Object[] args  = pjp.getArgs();
            log.info("begin invoke uri ::::: < {} >,params= {}" , uri,args.toString());
        } catch (Exception e) {
            log.error("AopUtils  before excution !", e);
        }
    }

    public void after(Object result) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        try {
            endTime = System.currentTimeMillis();
            long cost = endTime - beginTime;
            log.info("end invoke  uri  ::::::: < {}  >,result={} ,cost= {} ms",uri,result,cost);
        } catch (Exception e) {
            log.error("AopUtils  after Exception !", e);
        }
    }





}
