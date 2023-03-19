package cn.tedu.jsdvn2203.csmall.passport.web;

import cn.tedu.jsdvn2203.csmall.passport.exception.ServiceException;
import lombok.Data;

@Data
public class JsonResult {
    /**
     *  業務狀態碼
     */
    private Integer code;
    /**
     * 錯誤時的提示描述
     */
    private String  message;
    /**
     * 處理成功時,需要響應到客戶端的數據
     */
    private Object data;
    /**響應成功(不帶有數據)*/
    public static JsonResult ok(){
//        JsonResult result = new JsonResult();
//        result.setCode(ServiceCode.OK);
//        result.setData(null);
        return ok(null);
    }
    /**響應成功(帶有數據)*/
    public static JsonResult ok(Object data){
        JsonResult result = new JsonResult();
        result.setCode(ServiceCode.OK);
        result.setData(data);
        return result;
    }


    public static JsonResult fail(ServiceException e){
        return fail(e.getServiceCode(),e.getMessage());
    }

    public static JsonResult fail(Integer code,String message){
        JsonResult result = new JsonResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }





}
