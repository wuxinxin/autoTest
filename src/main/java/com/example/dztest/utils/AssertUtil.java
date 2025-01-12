package com.example.dztest.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.dztest.domain.HttpResponse;
import com.jayway.jsonpath.JsonPath;
import org.testng.Assert;

/**
 * Created by liang.pi on 2020/4/17.
 */
public class AssertUtil{
    public static <T>Object getData(HttpResponse response, String jsonPath){
        JSONObject body = JSONObject.parseObject(response.getBody());
        return JsonPath.read(body, jsonPath);
    }

    public static <T> void assertBodyEquals(HttpResponse response, String jsonPath, T expect){
        JSONObject body = JSONObject.parseObject(response.getBody());
        Assert.assertEquals(JsonPath.read(body, jsonPath), expect);
    }

    public static <T> void assertBodyNotEquals(HttpResponse response, String jsonPath, T expect){
        JSONObject body = JSONObject.parseObject(response.getBody());
        Assert.assertNotEquals(JsonPath.read(body, jsonPath), expect);
    }

    public static <T extends String> void assertBodyContains(HttpResponse response, String jsonPath, T expect){
        JSONObject body = JSONObject.parseObject(response.getBody());
        Assert.assertTrue(((String)JsonPath.read(body, jsonPath)).contains(expect));
    }

    public static <T extends String> void assertBodyNotContains(HttpResponse response, String jsonPath, T expect){
        JSONObject body = JSONObject.parseObject(response.getBody());
        Assert.assertFalse(((String)JsonPath.read(body, jsonPath)).contains(expect));
    }
}
