package cn.tedu.aop;


import cn.tedu.enums.ProcessCodeEnum;
import cn.tedu.exception.ProcessException;
import cn.tedu.pojo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionAdice {


    @ResponseBody
    @ExceptionHandler(value = ProcessException.class)
    public ResultVO<Object> ProcessExceptionHandler(ProcessException exception) {
        String errorCode = exception.getErrorCode();
        if (ProcessCodeEnum.warmLogEnumListContains(errorCode)) {
            //不记录日志
        } else if (ProcessCodeEnum.warmLogEnumListContains(errorCode)) {
            log.warn("拦截自定义异常： [{}] {}\n\r", errorCode, exception.getMessage(), exception);
        } else {
            log.error("拦截自定义异常： [{}] {}\n\r", errorCode, exception.getMessage(), exception);
        }

        return new ResultVO<Object>(errorCode, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultVO<Object> ProcessExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldError> allErrors = exception.getBindingResult().getFieldErrors();
        FieldError fieldError = allErrors.get(0);
        String errMsg = fieldError.getDefaultMessage();
        log.error("拦截参数校验异常： [{}] {}\n\r", ProcessCodeEnum.PARM_WARM.getCode(), errMsg, exception);

        return ProcessCodeEnum.PARM_WARM.buildFailResultVO(errMsg);
    }

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ResultVO<Object> errorHandler(Throwable exception) {
        log.error("拦截到未知异常", exception);
        return ProcessCodeEnum.FAIL.buildFailResultVO();
    }


}
