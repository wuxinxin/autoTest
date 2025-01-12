package com.example.dztest.service.api_action;

import com.alibaba.fastjson.JSONObject;
import com.example.dztest.apicase.common.GlobalApiVar;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.basic.BasicApi;
import com.example.dztest.utils.uilogger.UILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static com.example.dztest.domain.GlobalVar.GLOBAL_DATA_LIST;
import static com.example.dztest.domain.GlobalVar.GLOBAL_DATA_MAP;

@Service
public class ABasicApi {
    @Autowired
    private BasicApi basicApi;

    @Value("${login.account}")
    private String account;

    @Value("${login.pwd}")
    private String pwd;

    @UILogger(module = "ABasicApi Api", desc = "登录获取Authorization")
    public void login() {
        if (GlobalVar.GLOBAL_DATA_MAP.get("Authorization") == null || !GLOBAL_DATA_MAP.get("email").equals(account)) {
            HttpResponse httpResponse = basicApi.login(account, pwd);
            JSONObject obj = JSONObject.parseObject(httpResponse.getBody());
//            Assert.assertEquals(obj.get("message"), "请求成功");
            Assert.assertEquals(obj.get("code"), "10000");

            JSONObject jsonObject = JSONObject.parseObject(httpResponse.getBody());
            JSONObject data = (JSONObject) jsonObject.get("data");

            //登录信息，写入全局变量
            GlobalApiVar.globalVars.put("login.tenantId", data.get("tenantId").toString());
            GlobalApiVar.globalVars.put("login.userId", data.get("id").toString());

            GLOBAL_DATA_MAP.put("tenantId", data.get("tenantId"));
            GLOBAL_DATA_MAP.put("userId", data.get("id"));
            Map<String, Object> map = new HashMap<>();
            map.put("Authorization", httpResponse.getHeader("Authorization"));
            map.put("email", data.get("email"));
            map.put("tenantId", data.get("tenantId"));
            map.put("userId", data.get("id"));
            //深拷贝
            GLOBAL_DATA_MAP.putAll(map);

            GLOBAL_DATA_LIST.add(map);
        }
    }

    public void login(String account, String pwd) {
        HttpResponse httpResponse = basicApi.login(account, pwd);

        JSONObject jsonObject = JSONObject.parseObject(httpResponse.getBody());
        JSONObject data = (JSONObject) jsonObject.get("data");

//        GLOBAL_DATA_MAP.put("Authorization", httpResponse.getHeader("Authorization"));
//        GLOBAL_DATA_MAP.put("tenantId", data.get("tenantId"));
//        GLOBAL_DATA_MAP.put("phone", data.get("phone"));
//        GLOBAL_DATA_MAP.put("username", data.get("username"));
//        GLOBAL_DATA_MAP.put("userId", data.get("id"));
//        GLOBAL_DATA_MAP.put("roleName", data.get("roleName"));
//        GLOBAL_DATA_MAP.put("email", data.get("email"));

        Map<String, Object> map = new HashMap<>();
        map.put("Authorization", httpResponse.getHeader("Authorization"));
        map.put("email", data.get("email"));
        map.put("tenantId", data.get("tenantId"));
        //深拷贝
        GLOBAL_DATA_MAP.putAll(map);

        GLOBAL_DATA_LIST.add(map);
    }

    /*
    * c端请求，Authorization设置
    * */
    public void cLogin(String auth) {
        if (GlobalVar.GLOBAL_DATA_MAP.get("Authorization") != auth) {
            Map<String, Object> map = new HashMap<>();
            map.put("Authorization", auth);
            map.put("email", null);
            map.put("tenantId", null);
            //深拷贝
            GLOBAL_DATA_MAP.putAll(map);

            GLOBAL_DATA_LIST.add(map);
        }

    }

    /*
    * 切换到指定登录Authorization
    * */
    public void switchLogin(int index) {
        GLOBAL_DATA_MAP.putAll(GLOBAL_DATA_LIST.get(index));
    }

}
