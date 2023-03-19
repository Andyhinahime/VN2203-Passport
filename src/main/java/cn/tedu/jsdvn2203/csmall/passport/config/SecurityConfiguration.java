package cn.tedu.jsdvn2203.csmall.passport.config;

import cn.tedu.jsdvn2203.csmall.passport.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //開啟全局權限授權訪問設定
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    上面是通過打"au"快速重寫
//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //可放行的白名單
    String[] urls = {
            "/admins/login",
            "/doc.html",
            "/**/*.css",
            "/**/*.js",
            "/swagger-resources", //通過瀏覽器F12 去確認那些標頭沒有放行
            "/v2/api-docs",        //通過瀏覽器F12 去確認那些標頭沒有放行
            "/favicon.ico"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();//允許通過使用者端OPTIONS類型的請求
        http.csrf().disable(); //禁止防止跨域訪問

        //  "/*" - 只匹配一層的路徑 "/admins" "/brands"
        //  "/**" - 匹配若干層級路徑
        http.authorizeRequests() //請求須被授權才可訪問
                .antMatchers(urls) //匹配某些路徑
                .permitAll() //允許直接訪問(不需要經過認證授權)
                .anyRequest() //除了以上配置的任何其他請求
                .authenticated(); //已經通過認證才能訪問

        //添加處理Jwt的過濾器,必須在執行處理用戶名及密碼過濾器(Security內置)之前
        //在UsernamePasswordAuthenticationFilter.class(Security內置) 之前執行wtAuthorizationFilter
        http.addFilterBefore(jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter.class);
    }
}
