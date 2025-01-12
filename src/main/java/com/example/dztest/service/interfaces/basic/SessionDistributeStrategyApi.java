package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.db.rollback.db.Row;

/*
 * 会话智能分配
 * */
@Mapping(path = "/basic", url = "${url.basic}")
public interface SessionDistributeStrategyApi {
    /*
     * 保存/更新“仅人工客服”策略
     * 入参：
     * name  策略名称
     * productId 策略选中的渠道
     * accessStaff 策略选中的在线客服技能组id（im_basic_staff）
     * */
    @Post(path = "/api/v1/session/strategy/add", description = "保存/更新策略")
    HttpResponse addStrategyOnlyArtificial(@Raw("$.name") String name,
                                           @Raw("$.productRelationList[0].productId") String productId,
                                           @Raw("$.turnImConfig.accessStaff") Integer accessStaff);
    /*
    * 更新策略时需要传入策略id，否则会当成添加来处理
    */
    @Post(path = "/api/v1/session/strategy/add", description = "更新策略")
    HttpResponse updateStrategyOnlyArtificial(@Raw("$.name") String name,
                                              @Raw("$.productRelationList[0].productId") String productId,
                                              @Raw("$.turnImConfig.accessStaff") Integer accessStaff,
                                              @Raw("$.id") Integer id);

    /*
     * 保存/更新“仅机器人”策略
     * 入参：
     * name  策略名称
     * robotId 机器人id
     * */
    @Post(path = "/api/v1/session/strategy/add", description = "保存/更新策略")
    HttpResponse addStrategyOnlyRobot(@Raw("$.name") String name,
                                      @Raw("$.productRelationList[0].productId") String productId,
                                      @Raw("$.robotId") String robotId);

    /*
     * 保存/更新“机器人优化”策略
     * 入参：
     * name  策略名称
     * productId 策略选中的渠道
     * accessStaff 策略选中的在线客服技能组id（im_basic_staff）
     * */
    @Post(path = "/api/v1/session/strategy/add", description = "保存/更新策略")
    HttpResponse addStrategyRobotFirst(@Raw("$.name") String name,
                                       @Raw("$.robotId") String robotId,
                                       @Raw("$.productRelationList[0].productId") String productId,
                                       @Raw("$.turnImConfig.accessStaff") Integer accessStaff);

    /*
     * 保存/更新“人工优化”策略
     * 入参：
     * name  策略名称
     * productId 策略选中的渠道
     * accessStaff 策略选中的在线客服技能组id（im_basic_staff）
     * */
    @Post(path = "/api/v1/session/strategy/add", description = "保存/更新策略")
    HttpResponse addStrategyArtificialFirst(@Raw("$.name") String name,
                                            @Raw("$.robotId") String robotId,
                                            @Raw("$.productRelationList[0].productId") String productId,
                                            @Raw("$.turnImConfig.accessStaff") Integer accessStaff);

    /*
     * 删除策略
     * */
    @Delete(path = "/api/v1/session/strategy/{strategyID}/delete", description = "删除策略")
    HttpResponse delStrategy(@PathVariable("strategyID") Integer strategyID);

    /*
     * 获得策略
     * */
    @Get(path = "/api/v1/session/strategy/", description = "获取策略")
    HttpResponse getStrategy();

    /**
     * 开启关闭策略
     *
     * @param strategyID
     * @return
     */
    @Put(path = "/api/v1/session/strategy/{strategyID}", description = "开启关闭策略")
    HttpResponse openCloseStrategy(
            @PathVariable("strategyID") Integer strategyID,
            @Param("status") String status
    );
}
