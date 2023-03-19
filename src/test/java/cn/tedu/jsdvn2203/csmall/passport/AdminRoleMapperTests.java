package cn.tedu.jsdvn2203.csmall.passport;

import cn.tedu.jsdvn2203.csmall.passport.mapper.AdminRoleMapper;
import cn.tedu.jsdvn2203.csmall.passport.pojo.entity.AdminRole;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AdminRoleMapperTests {
    @Autowired
    AdminRoleMapper adminRoleMapper;
    @Test
    public void addNew(){
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(5L);
        adminRole.setRoleId(1L);
        int rows = adminRoleMapper.insert(adminRole);
        log.debug("插入成功,受影響的行數:{}",rows);
    }

}
