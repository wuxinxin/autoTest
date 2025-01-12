package com.example.dztest.service.api_action.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.admin.AdminApi;
import com.example.dztest.utils.uilogger.UILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static com.example.dztest.domain.GlobalVar.A_GLOBAL_DATA_LIST;
import static com.example.dztest.domain.GlobalVar.A_GLOBAL_DATA_MAP;

@Service
public class AAdminApi {
    @Autowired
    private AdminApi adminApi;

    @Value("${a.login.account}")
    private String account;

    @Value("${a.login.pwd}")
    private String pwd;

    @UILogger(module = "ABasicApi Api", desc = "A端登录获取Authorization")
    public void login() {
        if (A_GLOBAL_DATA_MAP.get("Authorization") == null || !A_GLOBAL_DATA_MAP.get("email").equals(account)) {
            HttpResponse httpResponse = adminApi.login(account, pwd);
            JSONObject obj = JSONObject.parseObject(httpResponse.getBody());
//            Assert.assertEquals(obj.get("message"), "请求成功");
            Assert.assertEquals(obj.get("code"), "10000");

            JSONObject jsonObject = JSONObject.parseObject(httpResponse.getBody());
            JSONObject data = (JSONObject) jsonObject.get("data");

//            GLOBAL_DATA_MAP.put("tenantId", data.get("tenantId"));
            A_GLOBAL_DATA_MAP.put("userId", data.get("id"));
            Map<String, Object> map = new HashMap<>();
            map.put("Authorization", httpResponse.getHeader("Authorization"));
            map.put("email", data.get("email"));
//            map.put("tenantId", data.get("tenantId"));
            map.put("userId", data.get("id"));
            //深拷贝
            A_GLOBAL_DATA_MAP.putAll(map);

            A_GLOBAL_DATA_LIST.add(map);
        }
    }

    /*
    * 切换到指定登录Authorization
    * */
    public void switchLogin(int index) {
        A_GLOBAL_DATA_MAP.putAll(A_GLOBAL_DATA_LIST.get(index));
    }

}
