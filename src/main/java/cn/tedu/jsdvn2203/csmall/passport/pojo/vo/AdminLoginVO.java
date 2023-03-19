package cn.tedu.jsdvn2203.csmall.passport.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdminLoginVO implements Serializable {
    private Long id;
    private String username;
    private String password;
    /**是否啟用 1=啟用 0=未啟用*/
    private Integer enable;
    /**權限表*/
    private List<String > permissions;

    private Integer loginCount;
}
