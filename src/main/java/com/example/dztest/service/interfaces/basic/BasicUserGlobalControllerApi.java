package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;
import org.springframework.beans.factory.annotation.Value;

/*
 * 用户服务 （员工管理）
 * */
@Mapping(path="/basic", url = "${url.basic}")
public interface BasicUserGlobalControllerApi {

    @UILogger(desc = "添加员工",isPage = false)
    @Post(path = "/api/v1/user/sub", description = "添加员工")
    HttpResponse addUser(@Param("name") String name,
                        @Param("email") String email,
                         @Param("phone") String phone,
                         @Param("empno") String empno,
                         @Param("roleIds") String roleIds
    );

    @Delete(path = "/api/v1/user/sub", description = "删除员工")
    HttpResponse delUser(@Param("id") String id);

    @Put(path = "/api/v1/user/sub", description = "修改员工")
    HttpResponse putUser(@Param("name") String name,
                         @Param("email") String email,
                         @Param("phone") String phone,
                         @Param("empno") String empno,
                         @Param("roleIds") String roleIds,
                         @Param("id") String id
    );

    @Put(path = "/api/v1/user/password/resetForCustom", description = "重置密码")
    HttpResponse restPwd(@Param("newPassword") String newPassword,
                         @Param("email") String email
    );

    @Get(path = "/api/v1/user/ext/page/front", description = "查询员工,搜索指定员工")
    HttpResponse searchUser(@Param("searchInfo") String searchInfo);

    @Put(path = "/api/v1/user/status", description = "更新用户状态 0:禁用 1:启用")
    HttpResponse putUserStatus(@Param("uid") String uid,
                               @Param("status") Integer status);


}

