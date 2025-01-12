package com.example.dztest.apicase.common;

import com.alibaba.fastjson.JSONObject;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.domain.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Allure;
import net.minidev.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author xinxin.wu
 * @description: http组装数据处理
 * @date 2023/10/11
 * @version: 1.0
 */
public class CaseExcelUtil {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @description: 解析断言数据字符串，返回断言数据集合
     * @param assertion 原始断言数据
     * @return Map<String, String>   断言数据的集合
     */
    public static Map<String, Map<String, String>> getAssertMap(String assertion) {
        Map<String, String> mapEq = new HashMap<>();
        Map<String, String> mapNoEq = new HashMap<>();
        Map<String, String> mapPattern = new HashMap<>();
        Map<String, Map<String, String>> mapMap = new HashMap<>();

        if (null == assertion || "".equals(assertion)) {
            return null;
        }
        String assertStr = getCommonParam(assertion);

//        Pattern pattern = Pattern.compile("([^;=]*)=([^;]*)");
//        Pattern pattern = Pattern.compile("([^;=!=]*)(=|!=)([^;]*)");
        Pattern pattern = Pattern.compile("([^;!]*[==]*)(=~|=|!=)([^;]*)");

        Matcher m = pattern.matcher(assertStr.trim());

        //利用;将字符串分隔开来
        String[] asserts = assertStr.split(";");

        //遍历save  msg_id=$.jsonPath.xx；
        for (String ass : asserts) {
            //	正则表达式 由0-n个非；=组成的字符串 =  0-n个非;组成的字符串
            //对msg_id=ssss做正则匹配
            Matcher matcher = pattern.matcher(ass.trim());
            while (matcher.find()) {
                String key = matcher.group(1);
                String ope = matcher.group(2);
                String value = matcher.group(3);

                //将key value加到map
                if ("=".equals(ope)) {
                    mapEq.put(key, value);
                } else if ("!=".equals(ope)) {
                    mapNoEq.put(key, value);
                } else if ("=~".equals(ope)) {
                    mapPattern.put(key, value);
                } else {
                    System.out.println("不支持的断言方式");
                }
            }
        }
        mapMap.put("=", mapEq);
        mapMap.put("!=", mapNoEq);

        return mapMap;
    }

    /**
     * @description: 根据头信息，赋值httpRequest请求头
     * @param headersStr   头信息
     * @param httpRequest   http请求实体
     */
    public static void setHeaders(String headersStr, HttpRequest httpRequest) {
        //如果Authorization不为空，说明登录过
        if (GlobalVar.GLOBAL_DATA_MAP.get("Authorization") != null) {
            httpRequest.getHeaders().put("Authorization", GlobalVar.GLOBAL_DATA_MAP.get("Authorization"));
        }

        //Content-Type默认为application/json
        httpRequest.getHeaders().put("Content-Type", "application/json");

        if (null == headersStr || "".equals(headersStr)) {
            return;
        }

        Pattern pattern = Pattern.compile("([^;=]*)=([^;]*)");
        Matcher m = pattern.matcher(headersStr.trim());

        //phone=18191992233;empno=990033&id=
        //利用;将字符串分隔开来
        String[] headers = headersStr.split(";");

        //遍历save  msg_id=$.jsonPath.xx；
        for (String header : headers) {
            //	正则表达式 由0-n个非；=组成的字符串 =  0-n个非;组成的字符串
            //对msg_id=ssss做正则匹配
            Matcher matcher = pattern.matcher(header.trim());
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);

                //将key value加到headers
                httpRequest.getHeaders().put(key, value);
            }
        }
    }

    /**
     * @description: 解析请求路径，赋值httpRequest路径和参数
     * @param pathstr     请求路径，可以带参数，如/uss/api/v1/ussgroup?id=${jvm.id}&groupId=${jvm.groupId}
     * @param httpRequest http请求实体
     * @return void
     */
    public static void setPath(String pathstr, HttpRequest httpRequest) {
        if (null == pathstr || "".equals(pathstr)) {
            return;
        }

        String[] paths = pathstr.split("\\?");
        if (paths.length == 1) {
            //没有？，则路径后不带参数
            httpRequest.setPath(paths[0]);
        } else if (paths.length == 2) {
            //有1个？，则带参数
            httpRequest.setPath(paths[0]);
            String params = paths[1];
            setParams(params, httpRequest);
        } else {
            System.out.println("paths.length no in (1, 2)");
        }
    }

    /**
     * @description: 处理”执行“栏：1.替换变量和函数 2.根据设置添加用户变量 3.仅执行函数
     * @param execute 变量设置栏数据，如groupName=技能组${__time(yyyyMMddHHMMSS)};mytest1=${id};mytest=helloword;sleep(1)
     */
    public static void setUserVars(String execute) {
        if (null == execute || "".equals(execute)) {
            return;
        }

        //替换变量和函数
        String varsSetP = buildParam(execute);

        Pattern pattern = Pattern.compile("([^;=]*)=([^;]*)");
        Matcher m = pattern.matcher(varsSetP.trim());

        //phone=18191992233;empno=990033&id=
        //利用;将字符串分隔开来
        String[] vars = varsSetP.split(";");

        //遍历save  msg_id=$.jsonPath.xx；
        for (String var : vars) {
            //如果没有“=”，则是内置函数 todo
            if (!var.contains("=")) {
                CaseExcelUtil.exeFuntion(var);
                continue;
            }

            //	正则表达式 由0-n个非；=组成的字符串 =  0-n个非;组成的字符串
            //对msg_id=ssss做正则匹配
            Matcher matcher = pattern.matcher(var.trim());
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);

                //将key value加到用户变量map
                GlobalApiVar.userVars.put(key, value);
            }
        }
    }

    /**
     * 截取自定义方法正则表达式：${__xxx(param1,param2,...)}
     */
    protected static Pattern exePattern = Pattern.compile("(\\w*?)\\((([\\w\\\\\\/:\\.\\$]*,?)*)\\)");

    public static void exeFuntion(String param) {

        //这个funPattern是指${__fun(xxx,...)}的正则匹配模式
        Matcher m = exePattern.matcher(param);
        while (m.find()) {
            //匹配到的子字符串的第一个分组是(\w*?)，也就是函数名
            String funcName = m.group(1);
            //匹配到的子字符串的第二个分组是(([\w\\\/:\$\.]*,?)*)，也就是param1,param2...
            String args = m.group(2);
            Object value;
            // bodyfile属于特殊情况，不进行匹配，在post请求的时候进行处理
            if (FunctionUtil.isFunction(funcName)) {
                // 属于函数助手，调用那个函数助手获取。
                value = FunctionUtil.getValue(funcName, args.split(","));
                // 解析对应的函数失败
                Assert.assertNotNull(value, String.format("解析函数失败：%s。", funcName));
//                param = StringUtils.replace(param, m.group(), value);
            }
        }
    }

    /**
     * @description: 根据param类型，设置httpRequest的params或body值
     * @param paramstr  接口入参
     * @param httpRequest   http请求实体类
     * @return void
     */
    public static void setParams(String paramstr, HttpRequest httpRequest) {
        if (null == paramstr || "".equals(paramstr)) {
            return;
        }

        Pattern pattern = Pattern.compile("([^;=]*)=([^;]*)");
        Matcher m = pattern.matcher(paramstr.trim());
        if (!m.find() && !paramstr.contains("[FilePath]")) {
            //参数中没有“=”，并且不包含[FilePath]，则是直接赋值给body
            httpRequest.setBody(paramstr);
            return;
        }

        if (!m.find() && paramstr.contains("[FilePath]:")) {
            //参数中没有“=”，并且包含[FilePath]，则是文件上传请求
            String filePathStr = StringUtils.substringAfter(paramstr, "[FilePath]:");
            String basePath = CaseExcelUtil.class.getResource("/").getPath();
            String filePath = basePath.replaceFirst("test-classes", "classes") + filePathStr.trim();

            File file = new File(filePath);
            httpRequest.getFiles().add(file);
            return;
        }

        //phone=18191992233&empno=990033&id=
        //利用&将字符串分隔开来
        String[] params = paramstr.split("&");

        //遍历save  msg_id=$.jsonPath.xx；
        for (String param : params) {
            //	正则表达式 由0-n个非；=组成的字符串 =  0-n个非;组成的字符串
            //对msg_id=ssss做正则匹配
            Matcher matcher = pattern.matcher(param.trim());
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);

                //参数中有“=”，则赋值给param
                httpRequest.getParams().put(key, value);
            }
        }
    }

    /**
     * @description: 提取json串中的值添加为用户变量
     * @param body  json响应
     * @param allSave   json提取字符串，如msg_id=$.jsonPath.xx
     * @return void
     */
    public static void saveResult(JSONObject body, String allSave) {
        if (null == body || null == allSave
                || "".equals(allSave)) {
            return;
        }

        //first_currency=$.result[0].currency;first_name=$.result[0].name
        //将excel里save  msg_id=$.jsonPath.xx；字符串分隔开来
        String[] saves = allSave.split(";");

        //遍历save  msg_id=$.jsonPath.xx；
        for (String save : saves) {
            //	正则表达式 由0-n个非；=组成的字符串 =  0-n个非;组成的字符串
            //对msg_id=$.jsonPath.xx做正则匹配
            Pattern pattern = Pattern.compile("([^;=]*)=([^;]*)");
            Matcher m = pattern.matcher(save.trim());
            while (m.find()) {
                //将excel save字段里等式左边的key作为key
                //msg_id是匹配到到第一个分组group(1)
                //对左边调用getBuildValue函数可能只起到一个trim的作用
                String key = m.group(1);
                //根据excel里save字段的jsonPath提取响应里对应着jsonPath的值作为value
                //$.jsonPath.xx是匹配到到第二个分组group(2)
                String jsonPath = m.group(2);
                Object value = JsonPath.read(body, jsonPath);
                String value_str = "";
                if (value instanceof BigDecimal) {
                    value_str = ((BigDecimal) value).toPlainString();
                } else if (value instanceof JSONArray) {
                    value_str = ((JSONArray) value).get(0).toString();
                } else {
                    //其他类型如Boolen，直接转为string
                    value_str = value.toString();
                }

//                ReportUtil.log(String.format("存储公共参数   %s值为：%s.", key, value));
                GlobalApiVar.userVars.put(key, value_str);
                System.out.println("key is: "+key+",value_str is: "+ value_str);
                Allure.step("key is: "+key+",value_str is: "+ value_str);
            }
        }
    }

    /**
     * 截取变量正则表达式：${xx}
     */
    protected static Pattern replaceParamPattern = Pattern.compile("\\$\\{([^_][^_].*?)\\}");

    /**
     * @description: 将参数param中的${xxx}替换为公共参数池map中以${xxx}中的xxx作key的value,并返回处理过的参数
     * @param param 要替换变量的字符串
     * @return String   替换${xxx}后的字符串
     */
    public static String getCommonParam(String param) {
        //如果参数为空，返回空字符串
        if (null == param || "".equals(param)) {
            return "";
        }
        //这个正则表达式模式匹配的是${}
        Matcher m = replaceParamPattern.matcher(param);// 取公共参数正则
        while (m.find()) {
            //取匹配到到的所有子字符串中的第一个分组，其实这个正则表达式就只有一个分组，(  ${}  )
            String replaceKey = m.group(1);
            // 从公共参数池中获取值，以${xxx}中的xxx作key,取key对应的value
            Object value = getSaveData(replaceKey);
            // 如果公共参数池中未能找到对应的值，该用例失败。
            Assert.assertNotNull(value, String.format("格式化参数失败，公共参数中找不到%s。", replaceKey));
            //将参数中的${xxx}替换为公共参数池中的值
            if (value instanceof BigDecimal) {
                param = param.replace( m.group(),  ((BigDecimal) value).toPlainString());
            } else {
                param = param.replace( m.group(),  (CharSequence)value);
            }
        }
        return param;
    }


    /**
     * 截取自定义方法正则表达式：${__xxx(param1,param2,...)}
     */
//    protected static Pattern funPattern = Pattern.compile("\\$\\{__(\\w*?)\\((([\\w\\\\\\/:\\.\\$]*,?)*)\\)\\}");
//    protected static Pattern funPattern = Pattern.compile("\\$\\{__(\\w*?)\\((([\\w|\\-\\\\\\/:\\.\\$]*,?)*)\\)\\}");
    protected static Pattern funPattern = Pattern.compile("\\$\\{__(\\w*?)\\((([\\w|\\s|\\-\\\\\\/:\\.\\$]*,?)*)\\)\\}");

    /**
     * @description: 处理内置函数${__fucn(param1,param2,...)}以及变量${xxxx}
     * @param param 要替换内置函数及变量的字符串
     * @return String   替换内置函数和变量后的字符串
     */
    public static String buildParam(String param) {
        // 处理${xxx}
        param = getCommonParam(param);

        //这个funPattern是指${__fun(xxx,...)}的正则匹配模式
        Matcher m = funPattern.matcher(param);
        while (m.find()) {
            //匹配到的子字符串的第一个分组是(\w*?)，也就是函数名
            String funcName = m.group(1);
            //匹配到的子字符串的第二个分组是(([\w\\\/:\$\.]*,?)*)，也就是param1,param2...
            String args = m.group(2);
            Object value;
            // bodyfile属于特殊情况，不进行匹配，在post请求的时候进行处理
            if (FunctionUtil.isFunction(funcName)) {
                // 属于函数助手，调用那个函数助手获取。
                value = FunctionUtil.getValue(funcName, args.split(","));
                // 解析对应的函数失败
                Assert.assertNotNull(value, String.format("解析函数失败：%s。", funcName));
                param = StringUtils.replace(param, m.group(), value.toString());
            }
        }
        return param;
    }

    /**
     * @description: 遍历用户变量、全局变量集合，获取key对应的value；优先遍历用户变量集合
     * @param replaceKey 键值对的key
     * @return String   键值对的value
     */
    public static Object getSaveData(String replaceKey) {
        //用户变量中查找
        if (GlobalApiVar.userVars.containsKey(replaceKey)) {
            return GlobalApiVar.userVars.get(replaceKey);
        }
        //全局变量中查找  ${g.basic.userid}
        if (GlobalApiVar.globalVars.containsKey(replaceKey)) {
            return GlobalApiVar.globalVars.get(replaceKey);
        }

        return "";
    }


}
