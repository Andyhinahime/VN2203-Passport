package cn.tedu.jsdvn2203.csmall.passport.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;
@Slf4j
public class JwtUtils {
    /**
     * 簽名密鑰
     */
    private static final String SECRET_KEY = "himehina12809";
    /**
     * JWT過期時間:以分鐘為單位
     */
    private static final long EXPIRATION_IN_MINUTE = 7 * 24 * 60;

    /**
     * 私有化構造方法,避免外部隨意創建對象
     * */
    private JwtUtils() {
    }

    /**
     * 生成JWT
     * */
    public static String generate(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_IN_MINUTE * 60 * 1000); 

        String userJwt = Jwts.builder()
                .setHeaderParam("typ", "jwt")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        log.debug("用戶資料Jwt:{}", userJwt);
        return userJwt;
    }
    public static Claims parse(String jwt){
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt).getBody();
    }

}
