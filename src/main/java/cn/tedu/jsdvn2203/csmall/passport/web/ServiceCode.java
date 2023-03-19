package cn.tedu.jsdvn2203.csmall.passport.web;

public class ServiceCode {
    /**
     * 成功
     */
    public static final int OK = 20000;
    /**
     * 錯誤:數據格式有問題
     */
    public static final int ERR_BAD_REQUEST = 40000;
    /**
     * 錯誤:JWT數據錯誤,可能被惡意竄改
     */
    public static final int ERR_JWT_INVALID = 40001;
    /**
     * 錯誤:JWT過期
     */
    public static final int ERR_JWT_EXPIRED = 40300;
    /**
     * 錯誤:資料重複
     */
    public static final int ERR_CONFLICT = 40900;
    /**
     * 錯誤:插入資料失敗
     */
    public static final int ERR_INSERT = 50000;
    /**
     * 錯誤:刪除資料失敗
     */
    public static final int ERR_DELETE = 50001;
    /**
     * 錯誤:編輯資料失敗
     */
    public static final int ERR_UPDATE = 50002;
    /**
     * 錯誤:未處理的異常
     */
    public static final int ERR_UNKNOWN = 59999;


}
