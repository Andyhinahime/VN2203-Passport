package cn.tedu.jsdvn2203.csmall.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class JwtTests {

    @Test
    public void testGeneratedJwt(){
        Map<String ,Object> claims = new HashMap<>();
        claims.put("id",1234);
        claims.put("name","Tom");

        Date expirationDate = new Date(System.currentTimeMillis() + 1 * 60 * 1000);

        String jwt = Jwts.builder()
                //header(頭部):用於配置算法與結果數據的類型
                .setHeaderParam("typ","jwt")
                .setHeaderParam("alg","HS256")
                //payload(載荷):用於配置封裝到JWT的數據
                .setClaims(claims) //配置的數據
                .setExpiration(expirationDate) //有效時間
                //signature(簽名):用於指定算法與秘鑰
                .signWith(SignatureAlgorithm.HS256,"abcxyz123")
                //打包
                .compact();
        log.debug("JWT:{}",jwt);

        // header.payload.signature  分為三個部分
        // eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9
        // .
        // eyJuYW1lIjoiVG9tIiwiaWQiOjEyMzQsImV4cCI6MTY2NzcxOTU5Mn0
        // .
        // UbYIbg1M7c1R1k8tn5hLO4KzFj4vlIAGDNTbn5DLd2k
    }

    @Test
    public void testParserJwt(){
        String jwt = "eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjgzNDY4OTIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiIvYW1zL2FkbWluL2RlbGV0ZSJ9LHsiYXV0aG9yaXR5IjoiL2Ftcy9hZG1pbi9yZWFkIn0seyJhdXRob3JpdHkiOiIvYW1zL2FkbWluL3VwZGF0ZSJ9LHsiYXV0aG9yaXR5IjoiL3Btcy9wcm9kdWN0L2RlbGV0ZSJ9LHsiYXV0aG9yaXR5IjoiL3Btcy9wcm9kdWN0L3JlYWQifSx7ImF1dGhvcml0eSI6Ii9wbXMvcHJvZHVjdC91cGRhdGUifV0sInVzZXJuYW1lIjoicm9vdCJ9.ff3PRItWKjIfr1b254Q9tIT2oWZwLOE5oAXe8LcG658";
        Claims claims = Jwts.parser().setSigningKey("abcxyz123").parseClaimsJws(jwt).getBody();

        Object username = claims.get("username");
        Object authorities = claims.get("authorities");
        log.debug(" username: {}",username);
        log.debug(" authorities: {}",authorities);


    }





}











