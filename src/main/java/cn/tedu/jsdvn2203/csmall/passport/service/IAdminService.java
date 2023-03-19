package cn.tedu.jsdvn2203.csmall.passport.service;

import cn.tedu.jsdvn2203.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.jsdvn2203.csmall.passport.pojo.dto.AdminLoginDTO;
import cn.tedu.jsdvn2203.csmall.passport.pojo.vo.AdminListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAdminService {
    /**新增用戶*/
    @Transactional
    void addNew(AdminAddNewDTO adminAddNewDTO);
    /**用戶列表*/
    List<AdminListItemVO> list();
    /**用戶登入*/
    String login(AdminLoginDTO adminLoginDTO);

}
