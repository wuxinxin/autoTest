package com.example.dztest.feishu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.domain.HttpType;
import com.example.dztest.extentreport.db.domain.CaseInfo;
import com.example.dztest.extentreport.db.domain.CaseTask;
import com.example.dztest.utils.AssertUtil;
import com.example.dztest.utils.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.wiki.v2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author xinxin.wu
 * @date 2024/04/08
 * @version: 1.0
 */
public class FeishuClient {
    private static final Logger logger = LoggerFactory.getLogger(FeishuClient.class);

    /**
     *应用唯一标识
     */
    private final String appID = "xxx";

    /**
     *应用秘钥
     */
    private final String appSecret = "xxx";

    /**
     * 飞书构建client
     */
    private final Client client;

    /**
     * 工作空间id
     */
    private final String spaceId = "7158026737496948737";

    /**
     * 工作空间下，父wiki节点token；也是spreadsheetToken
     */
    private final String wikiNodeToken = "PU3jwcNCeiJsFdkg1iucnZb1nic";

    /**
     * “任务”工作表sheet_id
     */
    private String taskSheetId;

    /**
     * “任务”工作表,待插入第n行；也是“用例”工作表名称序号
     */
    private int num;

    /**
     * 飞书域名
     */
    private final String url = "https://open.feishu.cn";

    /**
     * 自建应用app_access_token
     */
    private String appAccessToken;

    public FeishuClient() {
        this.client = Client.newBuilder(this.appID, this.appSecret).build();
    }

    /**
     * 父wiki节点下，通过子节点名称查找对应子节点objtoken
     * @param nodeTitle 节点标题
     * @return 子节点对应objtoken；为null表示未找到匹配的节点
     * @throws UnsupportedEncodingException
     */
    public String getNodeChild(String nodeTitle) throws Exception {
        // 创建请求对象；父节点下最大保留50个子节点，分季度备份
        ListSpaceNodeReq req = ListSpaceNodeReq.newBuilder()
                .spaceId(this.spaceId)
                .pageSize(50)
                .parentNodeToken(this.wikiNodeToken)
                .build();

        // 发起请求
        ListSpaceNodeResp resp = this.client.wiki().v2().spaceNode().list(req);

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s"
                    , resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return null;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
        Node[] nodes = resp.getData().getItems();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getTitle().equals(nodeTitle)) {
                return nodes[i].getObjToken();
            }
        }

        return null;
    }

    /**
     * 创建知识空间节点
     * @param title 知识空间节点标题
     * @return 知识空间节点objToken
     * @throws Exception
     */
    public String createNode(String title) throws Exception {
        // 创建请求对象
        CreateSpaceNodeReq req = CreateSpaceNodeReq.newBuilder()
                .spaceId(this.spaceId)
                .node(Node.newBuilder()
                        .objType("sheet")
                        .parentNodeToken(this.wikiNodeToken)
                        .nodeType("origin")
                        .originNodeToken(this.wikiNodeToken)
                        .title(title)
                        .build())
                .build();

        // 发起请求
        CreateSpaceNodeResp resp = this.client.wiki().v2().spaceNode().create(req);

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s"
                    , resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return null;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
        Node node = resp.getData().getNode();
        return node.getObjToken();
    }

    /**
     * 获取app_access_token；接口操作前，需要调用
     * @throws Exception
     */
    public void initAppAcessToken() throws Exception {
        String path = "/open-apis/auth/v3/app_access_token/internal";

        // 构建http header
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // 构建http body
        Map<String, Object> datas = new HashMap<>();
        datas.put("app_id", this.appID);
        datas.put("app_secret", this.appSecret);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(datas);

        HttpResponse response = HttpUtil.request("", url, HttpType.POST, path, headers,
                null, null, body, new ArrayList<>());
        logger.info("请求路径：{}；请求返回 {}", path, response.toString());
        if (FeishuUtil.isSuccess(response)) {
            this.appAccessToken = (String) AssertUtil.getData(response, "$.tenant_access_token");
        }
    }

    /**
     * 获取要新建“用例”工作表的序号，也是“任务”工作表插入数据的行数
     * @return
     */
    public void getCaseSheetName(String spreadsheetToken) throws UnsupportedEncodingException {
        String path = "/open-apis/sheets/v3/spreadsheets/" + spreadsheetToken + "/sheets/query";
        // 构建http header
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + this.appAccessToken);

        HttpResponse response = HttpUtil.request("", url, HttpType.GET, path, headers,
                null, null, null, new ArrayList<>());
        logger.info("请求路径：{}；请求返回 {}", path, response.toString());
        if (!FeishuUtil.isSuccess(response)) {
            return;
        }
        JSONArray jsonArray = (JSONArray) AssertUtil.getData(response, "$.data.sheets");
        this.num = jsonArray.size() + 1;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ("任务".equals(jsonObject.getString("title"))) {
                this.taskSheetId = jsonObject.getString("sheet_id");
                break;
            }
        }
    }

    /**
     * 获取工作表指定标题对应的sheetId
     * @return
     */
    public String getSheetIdByTitle(String spreadsheetToken, String title) throws UnsupportedEncodingException {
        String path = "/open-apis/sheets/v3/spreadsheets/" + spreadsheetToken + "/sheets/query";
        // 构建http header
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + this.appAccessToken);

        HttpResponse response = HttpUtil.request("", url, HttpType.GET, path, headers,
                null, null, null, new ArrayList<>());
        logger.info("请求路径：{}；请求返回 {}", path, response.toString());
        if (!FeishuUtil.isSuccess(response)) {
            return null;
        }
        JSONArray jsonArray = (JSONArray) AssertUtil.getData(response, "$.data.sheets");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (title.equals(jsonObject.getString("title"))) {
                return jsonObject.getString("sheet_id");
            }
        }

        return null;
    }

    /**
     * 增加“用例”工作表
     * @param spreadsheetToken
     * @return
     */
    private String addSheet(String spreadsheetToken) throws UnsupportedEncodingException {
        String path = "/open-apis/sheets/v2/spreadsheets/" + spreadsheetToken + "/sheets_batch_update";

        // 构建http header
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + this.appAccessToken);

        // 构建http body
        String body = "{\n" +
                "    \"requests\": [\n" +
                "        {\n" +
                "            \"addSheet\": {\n" +
                "                \"properties\": {\n" +
                "                    \"title\": \"string\",\n" +
                "                    \"index\": 1\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String title = "用例-" + this.num;
        body = JsonPath.parse(body).set("requests[0].addSheet.properties.title", title).jsonString();

        HttpResponse response = HttpUtil.request("", url, HttpType.POST, path, headers,
                null, null, body, new ArrayList<>());
        logger.info("请求路径：{}；请求返回 {}", path, response.toString());
        if (!FeishuUtil.isSuccess(response)) {
            return null;
        }
        String sheetId = (String) AssertUtil.getData(response, "$.data.replies[0].addSheet.properties.sheetId");
        return sheetId;
    }

    /**
     * 更新工作表标题，并写入首行数据
     * @param spreadsheetToken
     * @return
     */
    public void updateSheet(String spreadsheetToken) throws UnsupportedEncodingException, JsonProcessingException {
        //获取”Sheet1“对应的sheetid
        String sheetId = this.getSheetIdByTitle(spreadsheetToken, "Sheet1");
        //写入首行数据
        CaseTask caseTask = new CaseTask();
        this.addFirstLine(spreadsheetToken, sheetId, caseTask.toListCN());

        String path = "/open-apis/sheets/v2/spreadsheets/" + spreadsheetToken + "/sheets_batch_update";

        // 构建http header
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + this.appAccessToken);

        // 构建http body
        String body = "{\n" +
                "    \"requests\": [\n" +
                "        {\n" +
                "            \"updateSheet\": {\n" +
                "                \"properties\": {\n" +
                "                    \"sheetId\": \"string\",\n" +
                "                    \"title\": \"string\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        body = JsonPath.parse(body).set("requests[0].updateSheet.properties.title", "任务").
                set("requests[0].updateSheet.properties.sheetId", sheetId).jsonString();

        HttpResponse response = HttpUtil.request("", url, HttpType.POST, path, headers,
                null, null, body, new ArrayList<>());
        logger.info("请求路径：{}；请求返回 {}", path, response.toString());
    }


    /**
     * 增加“用例”工作表，并写入用例数据
     * @param spreadsheetToken
     * @param caseInfos
     * @throws UnsupportedEncodingException
     */
    public void addSheetCaseInfo(String spreadsheetToken, ArrayList<CaseInfo> caseInfos) throws UnsupportedEncodingException, JsonProcessingException {
        //添加"用例"工作表
        String sheetId = this.addSheet(spreadsheetToken);
        //添加首行
        CaseInfo caseInfo = new CaseInfo();
        this.addFirstLine(spreadsheetToken, sheetId, caseInfo.toListCN());

        //调用接口，追加用例数据
        String path = "/open-apis/sheets/v2/spreadsheets/" + spreadsheetToken + "/values_append";
        // 构建http header
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + this.appAccessToken);

        // 构建http body
        Map<String, Object> datas = new HashMap<>();
        String range = sheetId + "!" + "A" + 2 + ":" + "M" + (caseInfos.size() + 1);
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < caseInfos.size(); i++) {
            values.add(caseInfos.get(i).toList());
        }
        Map<String, Object> valueRange = new HashMap<>();
        valueRange.put("range", range);
        valueRange.put("values", values);
        datas.put("valueRange", valueRange);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(datas);

        HttpResponse response = HttpUtil.request("", url, HttpType.POST, path, headers,
                null, null, body, new ArrayList<>());
        logger.info("请求路径：{}；请求返回 {}", path, response.toString());
    }

    /**
     * “任务”工作表中追加任务数据
     */
    public void addCaseTask(String spreadsheetToken, CaseTask caseTask) throws UnsupportedEncodingException, JsonProcessingException {
        String path = "/open-apis/sheets/v2/spreadsheets/" + spreadsheetToken + "/values_append";

        // 构建http header
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + this.appAccessToken);

        // 构建http body
        Map<String, Object> datas = new HashMap<>();
        String range = this.taskSheetId + "!" + "A" + num + ":" + "M" + num;
        List<Object> values = new ArrayList<>();
        values.add(caseTask.toList());
        Map<String, Object> valueRange = new HashMap<>();
        valueRange.put("range", range);
        valueRange.put("values", values);
        datas.put("valueRange", valueRange);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(datas);

        HttpResponse response = HttpUtil.request("", url, HttpType.POST, path, headers,
                null, null, body, new ArrayList<>());
        logger.info("请求路径：{}；请求返回 {}", path, response.toString());
    }

    /**
     * “任务”“用例”工作表中追加首行，在增加工作表后立即调用
     */
    public void addFirstLine(String spreadsheetToken, String taskSheetId, List<String> list) throws UnsupportedEncodingException, JsonProcessingException {
        String path = "/open-apis/sheets/v2/spreadsheets/" + spreadsheetToken + "/values_append";

        // 构建http header
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + this.appAccessToken);

        // 构建http body
        Map<String, Object> datas = new HashMap<>();
        String range = taskSheetId + "!" + "A" + 1 + ":" + (char) (list.size() + 64) + 1;
        List<Object> values = new ArrayList<>();
        values.add(list);
        Map<String, Object> valueRange = new HashMap<>();
        valueRange.put("range", range);
        valueRange.put("values", values);
        datas.put("valueRange", valueRange);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(datas);

        HttpResponse response = HttpUtil.request("", url, HttpType.POST, path, headers,
                null, null, body, new ArrayList<>());
        logger.info("请求路径：{}；请求返回 {}", path, response.toString());
    }
}
