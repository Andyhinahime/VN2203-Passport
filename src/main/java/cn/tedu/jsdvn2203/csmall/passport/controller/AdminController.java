package cn.tedu.jsdvn2203.csmall.passport.controller;

import cn.tedu.jsdvn2203.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.jsdvn2203.csmall.passport.pojo.dto.AdminLoginDTO;
import cn.tedu.jsdvn2203.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.jsdvn2203.csmall.passport.security.AdminDetails;
import cn.tedu.jsdvn2203.csmall.passport.security.LoginPrincipal;
import cn.tedu.jsdvn2203.csmall.passport.service.IAdminService;
import cn.tedu.jsdvn2203.csmall.passport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admins")
@Api(tags = "1.用戶管理")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @PostMapping("add-new")
    @ApiOperationSupport(order = 10)
    @ApiOperation("新增管理員")
    @PreAuthorize("hasAnyAuthority('/ams/admin/update')")
    public JsonResult addNew(@RequestBody  AdminAddNewDTO adminAddNewDTO){
        log.debug("AdminController.addNew");
        adminService.addNew(adminAddNewDTO);
        return JsonResult.ok();
    }

    @ApiOperation("管理員登入")
    @ApiOperationSupport(order = 20)
    @PostMapping("/login")
    public JsonResult login(@RequestBody  AdminLoginDTO adminLoginDTO){
        log.debug("接收到登入請求:{}",adminLoginDTO);
        String jwt = adminService.login(adminLoginDTO);
        return JsonResult.ok(jwt);
    }



    @GetMapping("")
    @ApiOperationSupport(order = 30)
    @ApiOperation("管理員列表")
    @PreAuthorize("hasAnyAuthority('/ams/admin/read')")
    //@AuthenticationPrincipal :表示後面的對象是透過 Authentication 中獲取的
    public JsonResult list(@AuthenticationPrincipal LoginPrincipal principal) {
        log.debug("接收到管理員列表請求...");

        /*log.debug("認證相關訊息:{}",authentication);
        LoginPrincipal principal =(LoginPrincipal) authentication.getPrincipal();*/
        Long id = principal.getId();
        String username = principal.getUsername();
        log.debug("認證訊息中的id:{} , 帳號:{}" ,id,username);
        List<AdminListItemVO> list = adminService.list();
        return JsonResult.ok(list);
    }


}
