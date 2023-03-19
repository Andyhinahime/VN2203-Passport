package cn.tedu.jsdvn2203.csmall.passport.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//@Data 由於父類中本身沒有無參構造器,會報錯,所以只能一個一個添加註解
@Setter
@Getter
@EqualsAndHashCode
@ToString(callSuper = true) // "callSuper = true" 由於toString只會對本類有效,添加這個則可以連父類一起toString
public class AdminDetails extends User {
    private Long id;
    private Integer loginCount;

    public AdminDetails(String username, String password,
                        boolean enabled, Collection<? extends GrantedAuthority> authorities ) {
        super(username, password, enabled,
                true, //帳號未過期
                true, //憑證未過期
                true, //帳號未鎖定
                authorities);

    }
}
