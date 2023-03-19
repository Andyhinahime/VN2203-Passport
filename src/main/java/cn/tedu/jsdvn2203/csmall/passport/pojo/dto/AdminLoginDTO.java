package cn.tedu.jsdvn2203.csmall.passport.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdminLoginDTO implements Serializable {


    @ApiModelProperty(value = "帳號")
    private String username;
    @ApiModelProperty(value = "密碼")
    private String password;



}
