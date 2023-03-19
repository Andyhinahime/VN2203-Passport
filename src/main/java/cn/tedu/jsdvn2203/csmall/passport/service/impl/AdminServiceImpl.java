package cn.tedu.jsdvn2203.csmall.passport.service.impl;

import cn.tedu.jsdvn2203.csmall.passport.config.BeanConfig;
import cn.tedu.jsdvn2203.csmall.passport.exception.ServiceException;
import cn.tedu.jsdvn2203.csmall.passport.mapper.AdminMapper;
import cn.tedu.jsdvn2203.csmall.passport.mapper.AdminRoleMapper;
import cn.tedu.jsdvn2203.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.jsdvn2203.csmall.passport.pojo.dto.AdminLoginDTO;
import cn.tedu.jsdvn2203.csmall.passport.pojo.entity.Admin;
import cn.tedu.jsdvn2203.csmall.passport.pojo.entity.AdminRole;
import cn.tedu.jsdvn2203.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.jsdvn2203.csmall.passport.security.AdminDetails;
import cn.tedu.jsdvn2203.csmall.passport.service.IAdminService;
import cn.tedu.jsdvn2203.csmall.passport.util.JwtUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.tedu.jsdvn2203.csmall.passport.web.ServiceCode.*;

@Service
@Slf4j
public class AdminServiceImpl implements IAdminService {
    @Autowired
    BeanConfig beanConfig;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    public BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override

    public void addNew(AdminAddNewDTO adminAddNewDTO) {
        String username = adminAddNewDTO.getUsername();
        int count = adminMapper.countByUsername(username);
        if (count > 0) {
            String message = "用戶創建失敗,用戶名[" + username + "]已存在";
            log.error(message);
            throw new ServiceException(ERR_CONFLICT, message);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewDTO, admin);
        String rawPassword = admin.getPassword();
        String encodePassword = passwordEncoder.encode(rawPassword);
        admin.setPassword(encodePassword);

        admin.setPhone("0");
        admin.setGmtCreate(beanConfig.dateTime());
        log.debug("管理員創建時間:{}",admin.getGmtCreate());
        int rows = adminMapper.insert(admin);
        if (rows != 1) {
            String message = "用戶創建失敗,服務器忙線中,請稍後重試!";
            log.error(message);
            throw new ServiceException(ERR_INSERT, message);
        }



        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(admin.getId());
        adminRole.setRoleId(2L);
        adminRole.setGmtCreate(beanConfig.dateTime());
        log.debug("管理員角色創建時間:{}",adminRole.getGmtCreate());
        rows = adminRoleMapper.insert(adminRole);
        if (rows != 1) {
            String message = "用戶角色創建失敗,服務器忙線中,請稍後重試!";
            log.error(message);
            throw new ServiceException(ERR_INSERT, message);
        }
    }

    @Override
    public List<AdminListItemVO> list() {
        return adminMapper.list();
    }

    @Override
    public String login(AdminLoginDTO adminLoginDTO) {
        log.debug("開始處理登入業務:{}", adminLoginDTO);
        //調用authenticationManager 執行Spring Security的認證
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        adminLoginDTO.getUsername(),
                        adminLoginDTO.getPassword()
                );
        //獲取返回結果
        Authentication loginResult = authenticationManager.authenticate(authentication);
        log.debug("認證方法的返回結果:{}", loginResult);

        //從返回結果Principal 本質就是User類型,是loadUserByUsername()方法返回的結果
        log.debug("獲取Principal:{}", loginResult.getPrincipal());

        AdminDetails adminDetails = (AdminDetails) loginResult.getPrincipal();

        Long id = adminDetails.getId();  //輸出測試用,可直接在下方生成寫即可,無須此段
        log.debug("登入成功的管理員id:{}",id);
        String username = adminDetails.getUsername();  //輸出測試用,可直接在下方生成寫即可,無須此段
        log.debug("登入成功的用戶名:{}", username);

        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities(); //輸出測試用,可直接在下方生成寫即可,無須此段
        log.debug("登入成功後,管理員權限:{}", authorities);

        //轉換成JSON格式
        String authoritiesString = JSON.toJSONString(authorities);
        log.debug("將管理員權限轉換為JSON:{}", authoritiesString);

        //生成Jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",adminDetails.getId());
        claims.put("username", username);
        claims.put("authorities", authoritiesString);

        //修改最後登入時間及登入次數
        adminMapper.updateGmtLastLoginById(adminDetails.getId(), beanConfig.dateTime(),
                adminDetails.getLoginCount() + 1);

        //優化至一個工具類中方便管理維護
        String userJwt = JwtUtils.generate(claims);
      /*  Date expirationDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); //有效時間為登入後24小時內

        String userJwt = Jwts.builder()
                .setHeaderParam("typ", "jwt")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, "abcxyz123")
                .compact();
        log.debug("用戶資料Jwt:{}", userJwt);*/
        //執行到此處,表示用戶名及密碼是匹配的

        log.debug("登入成功");
        return userJwt;
    }


}








