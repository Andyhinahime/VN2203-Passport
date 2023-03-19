package cn.tedu.jsdvn2203.csmall.passport.mapper;

import cn.tedu.jsdvn2203.csmall.passport.pojo.entity.AdminRole;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleMapper {
    /**插入*/
    int insert(AdminRole adminRole);
}
