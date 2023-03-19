package cn.tedu.jsdvn2203.csmall.passport.security;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *  當前登入的當事人相關訊息
 * */
@Data

public class LoginPrincipal implements Serializable {
    /**
     * 當前登入的用戶id
     */
    private Long id;
    /**
     * 當前登入的帳號
     */
    private String username;


}
