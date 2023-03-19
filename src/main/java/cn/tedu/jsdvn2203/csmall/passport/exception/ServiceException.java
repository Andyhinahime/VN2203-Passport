package cn.tedu.jsdvn2203.csmall.passport.exception;
/*
*  業務異常
*     檢查型異常類 - 繼承Exception
*     運行時異常類 - 繼承RuntimeException
* */
public class ServiceException extends RuntimeException{
    private Integer serviceCode;
    public ServiceException(Integer serviceCode,String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }
}
