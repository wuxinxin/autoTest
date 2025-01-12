package com.example.dztest.service.interfaces.admin;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

import java.util.Date;


@Mapping(path = "/basic", url = "admin")
public interface AdminBasicApi {

    @Get(path = "/front/config/a", description = "配置信息")
    HttpResponse front();

    @Post(path = "/api/v1/tenant/newTenant", description = "新增租户")
    HttpResponse newTenant(
            @Param("channelCode") String channelCode,
            @Param("companyName") String companyName,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("password") String password,
            @Param("domain") String domain
    );
    @Post(path = "/api/v1/tenant/expire/a", description = "设置租户到期时间")
    HttpResponse setExpireTime(@Param("expireTime") String expireTime,
                               @Param("tenantId") String tenantId
    );


    /**
     * @param mealTemplateId
     * 线路呼入分钟数-1,线路呼出分钟数-2,呼入机器人分钟数-3,
     * 外呼机器人分钟数-4,文本机器人有效问答数-5,短信条数剩余量-6
     * 呼叫中心坐席并发-7,在线客服坐席并发-8,工单坐席并发-9
     * 外呼机器人数-10,呼入机器人数-11,文本机器人数-12，助手机器人数-13
     * @param mealType
     * 追加资源-1,追加并发-2
     */
    @Post(path = "/api/v1/meal/order/buy", description = "追加并发/资源")
    HttpResponse orderBuy(
            @Raw("mealType") Integer mealType,
            @Raw("beginDate") String beginDate,
            @Raw("buyNum") Integer buyNum,
            @Raw("crmPresaleNo") String crmPresaleNo,
            @Raw("mealTemplateId") Integer mealTemplateId,
            @Raw("tenantId") String tenantId
    );
    @Get(path = "/api/v1/bill/account/getCurrentBalance/targetA", description = "获取当前余额信息")
    HttpResponse getCurrentBalance(
            @Param("optTenantId") String optTenantId
    );

    @Post(path = "/api/v1/bill/account/recharge", description = "余额充值")
    HttpResponse recharge(
            @Raw("num") Double num,
            @Raw("resourceCount") Double resourceCount,
            @Raw("totalAmount") Double totalAmount,
            @Raw("optTenantId") String optTenantId
    );

    @Post(path = "/api/v1/bill/account/setLineOfCredit", description = "余额透支充值")
    HttpResponse setLineOfCredit(
            @Raw("optTenantId") String optTenantId,
            @Raw("lineOfCredit") Double lineOfCredit

    );

    @Get(path = "/api/v1/base/meal", description = "获取呼出剩余分钟数、透支额度")
    HttpResponse baseMeal(@Param("selectTenantId") String selectTenantId);

    @Put(path = "/api/v1/base/meal/resource/limit", description = "设置呼入、呼出透支上限")
    HttpResponse setResourceLimit(
            @Param("tenantId") String tenantId,
            @Param("resourceType") Integer resourceType,
            @Param("resoruceLimit") Integer resoruceLimit,
            @Param("actualId") Integer actualId
    );
    @Get(path = "/api/v1/tenant/dict/online.staff.upper.limit", description = "获取在线客服坐席并发数")
    HttpResponse getOnlineUpperLimit(@Param("selectiveTenantId") String selectiveTenantId);
}
