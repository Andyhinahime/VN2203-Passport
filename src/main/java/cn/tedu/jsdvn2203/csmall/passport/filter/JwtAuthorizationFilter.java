package cn.tedu.jsdvn2203.csmall.passport.filter;

import cn.tedu.jsdvn2203.csmall.passport.config.BeanConfig;
import cn.tedu.jsdvn2203.csmall.passport.mapper.AdminMapper;
import cn.tedu.jsdvn2203.csmall.passport.security.LoginPrincipal;
import cn.tedu.jsdvn2203.csmall.passport.util.JwtUtils;
import cn.tedu.jsdvn2203.csmall.passport.web.JsonResult;
import cn.tedu.jsdvn2203.csmall.passport.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cn.tedu.jsdvn2203.csmall.passport.web.ServiceCode.*;

@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("執行到JwtAuthorizationFilter過濾器");

        //清除上下文:解析之前必須先清除之前的Security的上下文,不然會導致 登入後 登出之後 即使不帶JWT仍可訪問
        SecurityContextHolder.clearContext();

        //從請求頭中獲得Jwt
        String jwt = request.getHeader("Authorization");
        log.debug("jwt:{}",jwt);

        //先判斷是否獲取到有效的Jwt數據, 假如沒有Jwt數據 - 放行(因為登入或註冊等特殊狀況下,
        // 本身就尚未攜帶Jwt數據,故暫時放行,且需要跟 SecurityConfiguration 白名單進行配合設置
        // StringUtils.hasText() : 不為null && 不為isEmpty{} && 包含文本containsText()
        if(!StringUtils.hasText(jwt)){
            log.debug("請求頭中的Jwt數據是無效的,直接放行");
            filterChain.doFilter(request,response);
            return;
        }

        //解析
        Claims claims = null;
        try {
            //優化至一個工具類中方便管理維護
            claims = JwtUtils.parse(jwt);
           /* claims = Jwts.parser().setSigningKey("abcxyz123")
                    .parseClaimsJws(jwt).getBody();*/
        } catch (ExpiredJwtException e) {
            log.debug("解析JWT失敗,JWT已過期:類名:{} , 錯誤訊息:{}",e.getClass().getName(),e.getMessage());
            String errorMessage = "登入訊息過期,請重新登入";
            JsonResult jsonResult = JsonResult.fail(ERR_JWT_EXPIRED, errorMessage);
            String jsonResultString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset = UTF-8");
            response.getWriter().println(jsonResultString);
            return;
        } catch (MalformedJwtException e) { //第一部分遭惡意竄改或非法登入等
            log.debug("解析JWT失敗,JWT資料錯誤:類名:{} , 錯誤訊息:{}",e.getClass().getName(),e.getMessage());
            String errorMessage = "獲取登入訊息失敗,請重新登入";
            JsonResult jsonResult = JsonResult.fail(ERR_JWT_INVALID, errorMessage);
            String jsonResultString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset = UTF-8");
            response.getWriter().println(jsonResultString);
            return;
        } catch (SignatureException e) { //第二及第三部分惡意竄改或非法登入等
            log.debug("解析JWT失敗,簽名錯誤:類名:{} , 錯誤訊息:{}",e.getClass().getName(),e.getMessage());
            String errorMessage = "獲取登入訊息失敗,請重新登入";
            JsonResult jsonResult = JsonResult.fail(ERR_JWT_INVALID, errorMessage);
            String jsonResultString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset = UTF-8");
            response.getWriter().println(jsonResultString);
            return;
        } catch (Throwable e) {
            log.debug("解析JWT失敗,錯誤詳情:類名:{} , 錯誤訊息:{}",e.getClass().getName(),e.getMessage());
            String errorMessage = "獲取登入訊息失敗,請重新登入";
            JsonResult jsonResult = JsonResult.fail(ERR_JWT_INVALID, errorMessage);
            String jsonResultString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset = UTF-8");
            response.getWriter().println(jsonResultString);
            return;
        }

        Object id = claims.get("id");
        Object username = claims.get("username");
        log.debug("從Jwt中解析得到id:{}",id);
        log.debug("從Jwt中解析得到用戶名:{}",username);


        LoginPrincipal loginPrincipal = new LoginPrincipal();
        loginPrincipal.setId(Long.parseLong(id.toString())); //Object轉換成Long類型
        loginPrincipal.setUsername(username.toString()); //Object轉換成String類型

         /* //設置臨時權限(之後會通過資料庫獲取)
      GrantedAuthority authority = new SimpleGrantedAuthority("1");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);*/

        Object authoritiesString = claims.get("authorities");
        List<SimpleGrantedAuthority> authorities
                = JSON.parseArray(authoritiesString.toString(), SimpleGrantedAuthority.class);


        //解析成功後,數據存入到Spring Security上下文中
        //當登入成功後,密碼無須傳入,設置null

        /*//將id封存進去
        Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);*/

        //用對象封存進去設定當前登入的用戶相關訊息
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(loginPrincipal,null,authorities);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication); //需放入Authentication對象

        //放行
        filterChain.doFilter(request,response);

    }
}

















