package cn.tedu.jsdvn2203.csmall.passport;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@SpringBootTest
@Slf4j
public class MessageDigestMD5 {

    @Test
    public void test1MD5(){
        String rewPassword = "123123";
        String encodePassword = DigestUtils.md5DigestAsHex(rewPassword.getBytes());
        log.debug("原文:{} , 密文:{}",rewPassword,encodePassword); //4297f44b13955235245b2497399d7a93

    }

    @Test
    public void test2MD5(){ //加鹽
        String rewPassword = "123123";
        String salt = "abcdefg789";
        String encodePassword = DigestUtils.md5DigestAsHex((rewPassword+salt).getBytes());
        log.debug("原文:{} , 密文:{}",rewPassword,encodePassword);

    }

    @Test
    public void test3MD5(){ //隨機加鹽
        String rewPassword = "123123";
        String salt = UUID.randomUUID().toString();
        String encodePassword = DigestUtils.md5DigestAsHex((rewPassword+salt).getBytes());
        log.debug("原文:{} , 隨機文:{} , 密文:{}",rewPassword,salt,encodePassword);
    }

    @Test
    public void testBCrypt(){
        BCryptPasswordEncoder passwordEncoder
                = new BCryptPasswordEncoder();
        String rewPassword = "123123";
        String encodePassword = passwordEncoder.encode(rewPassword);
        log.debug("密文:{}",encodePassword); //使用此方式每次產生出來的密文都不相同
        // 密文:$2a$10$R945Ov5JQrhhzRWQzOYeHezjln6/DzMEjZBsTjrBo.8X0PhASyU0O
        // 密文:$2a$10$3eyfHxc3Rv3zUtnOGvyPsOcmc.exBeuGeQJP9IUWlvKg5gpVXU83K
        // 密文:$2a$10$IF8bAsMf5hmY10FjCZJ3VOnWZYN1WflSrVZVogbw1oBs0Zjw.6ySa
    }

    @Test
    public void testMatches(){

        String rewPassword = "123123";
        String encodePassword = "$2a$10$R945Ov5JQrhhzRWQzOYeHezjln6/DzMEjZBsTjrBo.8X0PhASyU0O";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(rewPassword, encodePassword);
        log.debug("匹配結果：{}",matches);


    }


}
















