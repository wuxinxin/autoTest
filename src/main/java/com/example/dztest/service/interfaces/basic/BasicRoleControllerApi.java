package com.example.dztest.service.interfaces.basic;


import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

import java.util.List;

/**
 * 角色权限管理
 */
@Mapping(path = "/basic", url = "${url.basic}")
public interface BasicRoleControllerApi {
    @Get(path = "/api/v1/role/query", description = "获取角色信息")
    HttpResponse getQuery();

    @Post(path = "/api/v1/role", description = "新增角色")
    HttpResponse addRole(@Param("roleName") String roleName);

    @Delete(path = "/api/v1/role", description = "删除角色")
    HttpResponse delRole(@Param("roleId") String roleId);

    @Get(path = "/api/v1/role/query", description = "通过名称获取角色信息")
    HttpResponse queryRoleByName(@Param("roleName") String roleName);

    @Get(path = "/api/v1/user/resource/common/tree", description = "查看角色权限树")
    HttpResponse roleTree();

    @Post(path = "/api/v1/role/res/selected", description = "配置菜单权限")
    HttpResponse configMenu(
            @Param("codes") String codes,
            @Param("roleId") String roleId
    );

    @Put(path = "/api/v1/user/sub", description = "修改员工角色权限")
    HttpResponse configEmpRole(
            @Param("name") String name,
            @Param("nickname") String nickname,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("empno") String empno,
            @Param("id") String id,
            @Param("roleIds") String roleIds,
            @Param("departmentIds") String departmentIds,
            @FormData("name") String name1,
            @FormData("nickname") String nickname1,
            @FormData("email") String email1,
            @FormData("phone") String phone1,
            @FormData("empno") String empno1,
            @FormData("id") String id1,
            @FormData("roleIds") String roleIds1,
            @FormData("departmentIds") String departmentIds1

    );
}
