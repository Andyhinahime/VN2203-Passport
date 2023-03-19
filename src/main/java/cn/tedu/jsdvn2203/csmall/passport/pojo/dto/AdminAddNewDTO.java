package cn.tedu.jsdvn2203.csmall.passport.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class AdminAddNewDTO implements Serializable {
    @ApiModelProperty(value = "用戶帳號",required = true,example = "abcd123456")
    @NotBlank(message = "請輸入有效的帳號")
    private String username;
    @ApiModelProperty(value = "用戶密碼",required = true,example = "123456")
    @NotBlank(message = "請輸入有效的密碼")
    private String password;
    @ApiModelProperty(value = "用戶姓名",required = true,example = "Andy")
    @NotBlank(message = "請輸入有效的名稱")
    private String nickname;
    @ApiModelProperty(value = "用戶頭像",required = true,example = "Andy.png")
    private String avatar;
    @ApiModelProperty(value = "用戶信箱",required = true,example = "admin@ip.com")
    @NotBlank(message = "請輸入有效的信箱")
    private String email;
    @ApiModelProperty(value = "用戶簡介",required = true,example = "樂觀,開郎...")
    private String description;
    private Integer enable;

}
