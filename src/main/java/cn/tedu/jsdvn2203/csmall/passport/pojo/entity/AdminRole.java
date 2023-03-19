package cn.tedu.jsdvn2203.csmall.passport.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理員與角色的關西表
 */
@Data
public class AdminRole implements Serializable {
    private Long id;
    private Long adminId;
    private Long roleId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
