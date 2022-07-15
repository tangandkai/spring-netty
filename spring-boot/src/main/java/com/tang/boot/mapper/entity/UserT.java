package com.tang.boot.mapper.entity;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户
 * @author tangwenkai
 * @date 2021-06-03 12:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserT implements Serializable {

    @ApiParam("id")
    private Integer id;

    @ApiParam("用户名")
    private String userName;

    @ApiParam("密码")
    private String password;

    @ApiParam("角色")
    private String roles;
}
