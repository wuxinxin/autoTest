package com.example.dztest.testTools.platformMakeData;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dztest.testTools.platformMakeData.model.SessionModel;
import com.example.dztest.utils.TimeUtil;
import com.jayway.jsonpath.JsonPath;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @description: 获取洞察平台处理后的会话数据
 * @author xinxin.wu
 * @date 2024/01/23
 * @version: 1.0
 */
public class GenerateSessionData {

    //从json文件中获取匹配的json对象
    public static JSONObject parserJsonFileS(String jsonFile) {
        try {
            FileReader fileReader = new FileReader(jsonFile); //创建文件阅读器对象
            BufferedReader bufferedReader = new BufferedReader(fileReader); //创建缓冲区阅读器对象

            StringBuilder stringBuilder = new StringBuilder(); //字符串构造器
            String line;

            while ((line = bufferedReader.readLine()) != null) { //按行读取文件内容
                stringBuilder.append(line); //将每行添加到字符串构造器中
            }

            bufferedReader.close(); //关闭缓冲区阅读器

            String jsonStr = stringBuilder.toString(); //获取完整的JSON字符串

            JSONObject jsonObject = JSON.parseObject(jsonStr);

            System.out.println("JSON Object:\n" + jsonObject.toString()); //输出JSONObject对象

            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //list输出到文件
    public static void ListToFile(List<String> stringList, String file) {
        try {
            // 创建FileWriter对象，用于向txt文件写入数据
//            FileWriter writer = new FileWriter(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Writer writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
            // 遍历列表，将每个元素写入txt文件
            for (String item : stringList) {
                writer.write(item + "\n");
            }
            // 关闭writer流
            writer.close();
            System.out.println("已成功将列表内容保存至" + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将源List按照指定元素数量拆分为多个List
     *
     * @param source 源List
     * @param splitItemNum 每个List中元素数量
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int splitItemNum) {
        List<List<T>> result = new ArrayList<List<T>>();

        if (source != null && source.size() > 0 && splitItemNum > 0) {
            if (source.size() <= splitItemNum) {
                // 源List元素数量小于等于目标分组数量
                result.add(source);
            } else {
                // 计算拆分后list数量
                int splitNum = (source.size() % splitItemNum == 0) ? (source.size() / splitItemNum) : (source.size() / splitItemNum + 1);

                List<T> value = null;
                for (int i = 0; i < splitNum; i++) {
                    if (i < splitNum - 1) {
                        value = source.subList(i * splitItemNum, (i + 1) * splitItemNum);
                    } else {
                        // 最后一组
                        value = source.subList(i * splitItemNum, source.size());
                    }
                    result.add(value);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //获取json文件绝对路径
        Class<?> clazz = GenerateSessionData.class;
        URL url = clazz.getResource("data.json");
        String jsonFile = url.getPath();


        String nowTime = TimeUtil.getNowTime("yyyyMMddHHMMSS");
        //会话excel输出文件名称
        String excelSession = "sessionData" + nowTime + ".xlsx";
        String excelSessionFile = "cases/" + excelSession;
        //会话csv输出文件名称
        String csvSessionFile = "cases/" + nowTime + "_session.csv";
        //sql输出文件名称
        String sqlFile = "cases/" + nowTime + ".sql";
        //uuid数据文件
        String uuidFie = "cases/" + nowTime + ".csv";

        //解析json文件 获取JSONObject对象
        JSONObject jsonObject = parserJsonFileS(jsonFile);
        JSONArray jsonArray = JsonPath.read(jsonObject, "$.data.list[1].sessions");

        //sql模板字符串
        String sqlTemplate = "insert into `intellect_content` (`id`, `tenant_id`, `business_line`, `business_id`, `content_role`, `content_role_id`, `content`, `seq_no`, `start_time`, `end_time`, `end_flag`, `external_data`, `create_time`, `update_time`, `is_deleted`, `delete_time`) values('id_value','msxf1667272967688548c4fca','call','business_id_value','customer','700325015','嗯好的。','10','1706077048589','1706077049099','1','{\\\"recordId\\\":\\\"0aad8e57-b8b6-4032-88a7-050a8edf0a49\\\"}','2024-01-24 14:17:29.447000','2024-01-24 14:17:29','0','2024-01-24 14:17:29.447000');";

        List<SessionModel> sessionModelList = new ArrayList<>();
        List<String> sqlStrList = new ArrayList<>();
        List<String> uuidStrList = new ArrayList<>();
        List<String> csvSessionList = new ArrayList<>();
        //造数：会话次数
        int num = 100;
        //初始id
        int id = 1000;
        for (int i = 0; i < num; i++) {
            String businessId = UUID.randomUUID().toString();
            //造数：生成得助侧intellect_content表入库sql
            id += 1;
            String sqlStr = sqlTemplate.replace("business_id_value", businessId);
            String sqlStr1 = sqlStr.replace("id_value", String.valueOf(id));
            sqlStrList.add(sqlStr1);

            //造数：生成uuid数据文件
            uuidStrList.add(businessId);

            //造数：遍历对话文本-在洞察平台生成对应会话数据
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject object = jsonArray.getJSONObject(j);

                SessionModel sessionModel = new SessionModel();
                // 对object进行操作
                String content = object.getString("content");
                Integer roleType = object.getInteger("roleType");
                Integer seqNo = object.getInteger("seqNo");
                String key = UUID.randomUUID().toString();

                //写入csvSessionList
                String csvSessionStr = key + "," + businessId + "," + content + "," + roleType.toString() + "," + seqNo;
                csvSessionList.add(csvSessionStr);

                //写入sessionModel
                sessionModel.setContent(content);
                sessionModel.setRoleType(roleType);
                sessionModel.setSeqNo(seqNo);
                sessionModel.setBusinessId(businessId);
                sessionModel.setKey(key);

                sessionModelList.add(sessionModel);
            }

        }

        //会话数据输出excel文件
        EasyExcel.write(excelSessionFile, SessionModel.class).sheet().doWrite(sessionModelList);
        //会话数据输出csv文件
        ListToFile(csvSessionList, csvSessionFile);
        //输出mysql文件
        ListToFile(sqlStrList, sqlFile);
        //输出uuid文件
        ListToFile(uuidStrList, uuidFie);

        //每个list中元素数量
        int n = 20;
        List<List<String>> results = averageAssign(uuidStrList, n);
        for (int i = 0; i < results.size(); i++) {
            String tempFile = "cases/" + n + "_" + i + ".csv";
            ListToFile(results.get(i), tempFile);
        }

    }
}
