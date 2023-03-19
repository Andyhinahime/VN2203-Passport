package cn.tedu.jsdvn2203.csmall.passport.security;

import cn.tedu.jsdvn2203.csmall.passport.mapper.AdminMapper;
import cn.tedu.jsdvn2203.csmall.passport.pojo.vo.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AdminMapper adminMapper;
    /**
     *  返回UserDetails對象
     * @param s 用戶名
     * @return  用戶詳情對象,以便後續對比操作登入等
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("Spring Security根據用戶名:[{}]查詢用戶的詳情資料",s);
       /* // 以下代碼為測試創建一個使用者登入,實際為通過資料庫獲取用戶詳情
        if ("andy12809".equals(s)){
            UserDetails userDetails = User.builder() //開始搭建
                                        .username("andy12809")
                                        .password("$2a$10$IF8bAsMf5hmY10FjCZJ3VOnWZYN1WflSrVZVogbw1oBs0Zjw.6ySa")
                                        .disabled(false) //帳號是否禁用
                                        .accountLocked(false) //帳號是否鎖定
                                        .accountExpired(false) //帳號是否過期
                                        .credentialsExpired(false) //帳號認證是否過期
                                        .authorities("指定權限(任意字符串 , *之後會透過資料庫獲取*)")
                                        .build(); //完成
            return userDetails;
        }*/
        AdminLoginVO adminLoginVO = adminMapper.getByUsername(s);

        if (adminLoginVO != null){
            /*   UserDetails userDetails = User.builder()
                    .username(adminLoginVO.getUsername())
                    .password(adminLoginVO.getPassword())
                    .disabled(adminLoginVO.getEnable() == 0)
                    .accountLocked(false)
                    .accountExpired(false)
                    .credentialsExpired(false)
                    .authorities(adminLoginVO.getPermissions().toArray(new String[]{}))
                    .build();
            log.debug("UserDetails :{}",userDetails);*/

            //獲取權限集合
            List<String> permissions = adminLoginVO.getPermissions();

            //創建對應權限集合類型並將上方的權限集合一一放入
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }

            //自定義的類
            AdminDetails adminDetails = new AdminDetails(
                    adminLoginVO.getUsername(),
                    adminLoginVO.getPassword(),
                    adminLoginVO.getEnable() == 1,
                    authorities
            );
            adminDetails.setId(adminLoginVO.getId());
            adminDetails.setLoginCount(adminLoginVO.getLoginCount());
            return adminDetails;
        }
        return null;
    }
}
