package com.example.dztest.service.interfaces.kms;


import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;

import java.util.List;

@Mapping(path = "/kms")
public interface RobotApi {

    @Post(path = "/api/v1/integration/robot/save", description = "新建机器人")
    HttpResponse save(
            @Raw("$.name") String name,
            @Raw("$.industryName") String industryName,
            @Raw("$.robotType") String robotType
    );

    @Get(path = "/api/v1/integration/robot/queryForPage", description = "机器人搜索查询")
    HttpResponse queryForPage(
            @Param("nameStr") String nameStr
    );

    @Delete(path = "/api/v1/integration/robot/delete/{robotId}", description = "删除机器人到回收站")
    HttpResponse deleteRobotToCollection(@PathVariable("robotId") String robotId);

    @Put(path = "/api/v1/integration/robot/finalDelete", description = "从回收站最终删除机器人")
    HttpResponse finalDelete(@Param("id") String id);

    @UILogger(desc = "新建问答知识")
    @Post(path = "/api/v1/integration/classification/saveOrUpdate", description = "新建问答知识")
    HttpResponse newAnswerQuestion(
            @Raw("$.classification.categoryId") String categoryId,
            @Raw("$.robotId") String robotId,
            @Raw("$.answer") String answer,
            @Raw("$.classification.standardAsk") String standardAsk
    );

    @Get(path = "/api/v1/integration/category/cls/tree", description = "机器人基础分类树信息")
    HttpResponse tree(@Param("robotId") String robotId,
                      @Param("type") String type);

    @Get(path = "/kms/api/v1/feign/cls/uniteRecommendList/c", description = "问题回答")
    HttpResponse uniteRecommendList(
            @Param("question") String question,
            @Param("robotId") String robotId,
            @Param("LOCAL_TOKEN") String LOCAL_TOKEN
    );

    @Get(path = "/api/v1/robot/task/center/add", description = "导入前先新增获取taskId,提供给导入接口使用")
    HttpResponse robotAdd(
            @Param("robotName") String robotName
    );

    @UILogger(desc = "导入机器人")
    @Post(path = "/api/v1/integration/robot/clone/import", description = "导入机器人")
    HttpResponse importRobot(
            @FilePath("file") String file,
            @FormData("robotName") String robotName,
            @FormData("taskId") String taskId
    );

    @Post(path = "/api/v1/integration/greeting/saveOrUpdate", description = "新建寒暄知识")
    HttpResponse newHxKnowledge(
            @Raw("$.robotId") String robotId,
            @Raw("$.greeting.standardAsk") String standardAsk,
            @Raw("$.greeting.categoryId") String categoryId,
            @Raw("$.answerList[0].answer") String answer
    );

    @Post(path = "/api/v1/integration/intention/saveOrUpdate", description = "新建任务意图")
    HttpResponse newTaskPurpose(
            @Raw("$.robotId") String robotId,
            @Raw("$.intention.name") String name,
            @Raw("$.expressionList[0].expression") String expression);


    @Get(path = "/api/v1/integration/intention/list", description = "任务意图列表")
    HttpResponse intentionList(
            @Param("robotId") String robotId);

    @Get(path = "/api/v1/robot/setting/guidewords", description = "获取引导语")
    HttpResponse introductionInfo(
            @Param("robotId") String robotId);

    @Get(path = "/api/v1/integration/robot/settings/im/query", description = "转人工规则信息")
    HttpResponse toManualInfo(
            @Param("robotId") String robotId);


    @Post(path = "/api/v1/multidimensional/entity/addOrEdit", description = "枚举实体新增或者编辑")
    HttpResponse entityAddOrEdit(
            @Raw("$.name") String name,
            @Raw("$.robotId") String robotId,
            @Raw("$.details[0].value") String value,
            @Raw("$.type") String type
    );

    @Post(path = "/api/v1/multidimensional/entity/list", description = "实体列表")
    HttpResponse entityList(
            @Raw("entityName") String entityName,
            @Raw("robotId") String robotId
    );

    @Post(path = "/api/v1/multidimensional/entity/addOrEdit", description = "正则实体新增或者编辑")
    HttpResponse regEntityAddOrEdit(
            @Raw("$.name") String name,
            @Raw("$.robotId") String robotId,
            @Raw("$..regex") String regex,
            @Raw("$.type") String type
    );

    @Post(path = "/api/v1/multidimensional/attribute/addOrEdit", description = "属性新增")
    HttpResponse attributeAddOrEdit(
            @Raw("$.attributeName") String attributeName,
            @Raw("$.robotId") String robotId,
            @Raw("$.similarityList") List<String> similarityList);


    @Post(path = "/api/v1/multidimensional/attribute/list", description = "属性列表")
    HttpResponse attributeList(
            @Raw("$.robotId") String robotId,
            @Raw("$.attributeName") String attributeName
    );

    @Post(path = "/api/v1/multidimensional/entity/robotEntities", description = "机器人全部属性获取")
    HttpResponse robotEntities(
            @Raw("$.robotId") String robotId);


    @Post(path = "/api/v1/multidimensional/knowledge/repository/add", description = "表格知识库新增")
    HttpResponse tableKnowledgeAdd(
            @Raw("$.robotId") String robotId,
            @Raw("$.columnCode") String columnCode,
            @Raw("$.repositoryName") String repositoryName);


    @Post(path = "/api/v1/multidimensional/knowledge/repository/list", description = "表格知识库列表")
    HttpResponse tableKnowledgeList(
            @Raw("$.repositoryName") String repositoryName,
            @Raw("$.robotId") String robotId);

    @Post(path = "/api/v1/multidimensional/knowledge/list", description = "表格知识列的列表")
    HttpResponse tableKnowledgeColumnList(
            @Raw("$.robotId") String robotId,
            @Raw("$.repositoryId") String repositoryId
    );


    @Post(path = "/api/v1/multidimensional/attribute/robotAttributes", description = "机器人属性名称列表")
    HttpResponse robotAttributes(
            @Raw("$.repositoryId") String repositoryId,
            @Raw("$.headCode") String headCode,
            @Raw("$.robotId") String robotId);

    @Post(path = "/api/v1/multidimensional/entity/robotEntityValues", description = "机器人属性值列表")
    HttpResponse robotEntityValues(
            @Raw("$.repositoryId") String repositoryId,
            @Raw("$.headCode") String headCode,
            @Raw("$.entityCode") String entityCode,
            @Raw("$.robotId") String robotId);


    /**
     * {
     * "robotId":"NEWcecd6618546b3af39503927eff799",
     * "repositoryId":700044001,
     * "headCode":"285fd663e4004e68acab17e4b921fd49",
     * "attributes":[
     * "57e334a4ebe647779df673faac27a176"
     * ]
     * }
     *
     * @param robotId
     * @param repositoryId
     * @return
     */
    @Post(path = "/api/v1/multidimensional/knowledge/manageAttributeColumn", description = "表格知识自定义属性列")
    HttpResponse manageAttributeColumn(
            @Raw("$.robotId") String robotId,
            @Raw("$.repositoryId") String repositoryId,
            @Raw("$.headCode") String headCode,
            @Raw("$.attributes") List<String> attributes
    );

    /**
     * {
     * "robotId":"NEWcecd6618546b3af39503927eff799",
     * "headCode":"285fd663e4004e68acab17e4b921fd49",
     * "repositoryId":700044001,
     * "valueIdList":[
     * 706372770
     * ],
     * "code":"2c2ae02ce7564bd2804c40d40ba5c194"
     * }
     */
    @Post(path = "/api/v1/multidimensional/knowledge/addEntityValue", description = "表格知识添加行")
    HttpResponse addEntityRawValue(
            @Raw("$.robotId") String robotId,
            @Raw("$.headCode") String headCode,
            @Raw("$.repositoryId") String repositoryId,
            @Raw("$.valueIdList[0]") Integer valueIdList,
            @Raw("$.code") String code
    );


    /**
     * {
     * "abscissa":1663143220352,
     * "headCode":"fb4bc3d6c6984f62adb561f1fe7d1b8d",
     * "id":null,
     * "ordinate":1,
     * "replyScript":"<p>华为</p>",
     * "columnCode":"a44293e62b404499b432173b81e924b3",
     * "columnValue":"华为",
     * "robotId":"NEW37d07527a446db531b88546498d32",
     * "repositoryId":700047001
     * }
     */
    @Post(path = "/api/v1/multidimensional/knowledge/editKnowledge", description = "编辑表格知识属性值")
    HttpResponse editKnowledge(
//            @Raw("$.abscissa") String abscissa,
            @Raw("$.headCode") String headCode,
            @Raw("$.replyScript") String replyScript,
            @Raw("$.columnCode") String columnCode,
            @Raw("$.columnValue") String columnValue,
            @Raw("$.robotId") String robotId,
            @Raw("$.repositoryId") String repositoryId
    );

    @Post(path = "/api/v1/entry/insert", description = "新增同义/停用词[wordType=1]/停用词[wordType=2]")
    HttpResponse newSameMeanWords(
            @Raw("$.robotId") String robotId,
            @Raw("$.standWord") String standWord,
            @Raw("$.wordType") String wordType,
            @Raw("$.similarWord") String similarWord
    );


    @Get(path = "/api/v1/entry/page", description = "同义词列表[wordType=1]/停用词[wordType=2]")
    HttpResponse getSameMeanWordsList(
            @Param("robotId") String robotId,
            @Param("wordType") String wordType
    );

    @Get(path = "/api/v1/integration/robot/clone/copy", description = "复制机器人")
    HttpResponse copyRobot(
            @Param("robotId") String robotId,
            @Param("robotName") String robotName
    );

    @Get(path = "/api/v1/robot/task/center/progress", description = "机器人复制、导入进度")
    HttpResponse getRobotCopyProgress(
            @Param("taskId") String taskId
    );

    @Get(path = "/api/v1/integration/robot/clone/export", description = "导出机器人")
    HttpResponse exportRobot(
            @Param("robotId") String robotId
    );

    @Get(path = "/api/v1/robot/task/center/list", description = "机器人平台任务列表")
    HttpResponse taskList(
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize
    );

    @Post(path = "/api/v1/integration/classification/list", description = "问答知识列表")
    HttpResponse classificationList(
            @Raw("$.robotId") String robotId,
            @Raw("$.categoryIdList") List<String> categoryIdList
    );

    @Post(path = "/api/v1/integration/greeting/list", description = "寒暄库列表")
    HttpResponse greetingList(
            @Raw("$.robotId") String robotId,
            @Raw("$.categoryIdList") List<String> categoryIdList
    );

    @Get(path = "/api/v1/tags/list", description = "标签列表")
    HttpResponse tagList(
            @Param("robotId") String robotId,
            @Param("orderBy") String orderBy,
            @Param("orderByType") String orderByType
    );

    @Get(path = "/api/v1/sms/list", description = "对话短信列表")
    HttpResponse smsList(
            @Param("robotId") String robotId
    );

    @Get(path = "/api/v1/blacklistRule/list", description = "黑名单规则列表")
    HttpResponse blackRuleList(
            @Param("robotId") String robotId
    );


}
