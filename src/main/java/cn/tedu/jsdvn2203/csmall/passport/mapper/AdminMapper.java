package cn.tedu.jsdvn2203.csmall.passport.mapper;

import cn.tedu.jsdvn2203.csmall.passport.pojo.entity.Admin;
import cn.tedu.jsdvn2203.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.jsdvn2203.csmall.passport.pojo.vo.AdminLoginVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AdminMapper {

    /**插入*/
    int insert(Admin admin);
    /**根據用戶名數量來確認是否唯一*/
    int countByUsername(String username);
    /**用戶列表*/
    List<AdminListItemVO> list();

    /**根據用戶名獲取用戶訊息*/
    AdminLoginVO getByUsername(String username);

    /**根據ID修改最後登入時間*/
    int updateGmtLastLoginById(Long id,
                               @Param("gmtLastLogin") LocalDateTime localDateTime,
                               @Param("loginCount") Integer count);






}
