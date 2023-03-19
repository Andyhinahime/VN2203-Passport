package cn.tedu.jsdvn2203.csmall.passport;

import cn.tedu.jsdvn2203.csmall.passport.mapper.AdminMapper;
import cn.tedu.jsdvn2203.csmall.passport.pojo.entity.Admin;
import cn.tedu.jsdvn2203.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.jsdvn2203.csmall.passport.pojo.vo.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class AdminMapperTests {
    @Autowired
    AdminMapper adminMapper;
    @Test
    public void testInsert(){
        Admin admin = new Admin();
        admin.setUsername("Andy1111");
        int rows = adminMapper.insert(admin);
        Long id = admin.getId();
        log.debug("插入成功,受影響的行數:{}, id:{}",rows,id);
    }
    @Test
    public void testCountByUsername(){
        String userName = "nobody";
        int rows = adminMapper.countByUsername(userName);
        log.debug("查詢完畢,共有{}條數據",rows);
    }
    @Test
    public void testList(){
        List<AdminListItemVO> list = adminMapper.list();
        log.debug("列表創建完成,共有{}條數據",list.size());
        for (AdminListItemVO adminListItemVO : list) {
            log.debug("Admin:{}\n",adminListItemVO);
        }
    }

    @Test
    public void getByUsername(){
        String username = "root";
        AdminLoginVO byUsername = adminMapper.getByUsername(username);
        log.debug("username:{}",byUsername);
    }

}
