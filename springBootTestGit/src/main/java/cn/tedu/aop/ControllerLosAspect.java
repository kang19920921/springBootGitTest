package cn.tedu.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerLosAspect {

    @Pointcut("execution(* cn.tedu.controller.*Controller.*(..))")
    public void excute() {

    }

    @Around("excute()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        AopLogUtil aopLogUtil = new AopLogUtil();
        aopLogUtil.before(pjp);
        Object result = pjp.proceed();
        aopLogUtil.after(result);

        return result;
    }

}
