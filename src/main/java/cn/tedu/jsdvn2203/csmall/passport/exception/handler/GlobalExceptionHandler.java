package cn.tedu.jsdvn2203.csmall.passport.exception.handler;

import cn.tedu.jsdvn2203.csmall.passport.exception.ServiceException;
import cn.tedu.jsdvn2203.csmall.passport.web.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;

import static cn.tedu.jsdvn2203.csmall.passport.web.ServiceCode.*;


/**
 * 統一異常處理類
 */
//@ResponseBody
//@ControllerAdvice
@RestControllerAdvice // 等同於 ResponseBody + ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 處理業務異常
     * @param e
     * @return
     */
    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult handleBindException(BindException e) {
        //提示:1個錯誤
        //String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        /**提示:多個錯誤 - 方式 1 (此方式在後面會多一個;)
        StringBuilder builder = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            builder.append(fieldError.getDefaultMessage());
            builder.append("; ");
        }
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST,builder.toString());
         */

        //提示:多個錯誤 - 方式 2 (推薦)
        //StringJoiner joiner = new StringJoiner("; " , "前綴" , "後綴");
        StringJoiner joiner = new StringJoiner("; ");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            joiner.add(fieldError.getDefaultMessage());
        }
        return JsonResult.fail(ERR_BAD_REQUEST,joiner.toString());
    }


    @ExceptionHandler
    public JsonResult handleThrowable(Throwable e){
        log.error("異常不明確:{},錯誤訊息:{}",e.getClass().getName(),e.getMessage());
        String message = "伺服器忙線中,請稍後在試,謝謝";
        return JsonResult.fail(ERR_UNKNOWN,message);
    }

}
