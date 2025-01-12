package com.example.dztest.utils;

import java.io.*;

public class JsonUtil {

    /**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @description: 读取文件，返回json字符串
     * @param jsonFile 类加载绝对路径文件
     * @return String json类型字符串
     */
    public static String readWithClassLoader(String jsonFile) throws IOException {
        ClassLoader  classLoader =  JsonUtil.class.getClassLoader();
        BufferedReader reader = null;
        InputStream inputStream = classLoader.getResourceAsStream(jsonFile);
        reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder content = new StringBuilder();
        String line = reader.readLine();
//        while (line != null && line.length() > 0) {
        while (line != null) {
            content.append(line);
            line = reader.readLine();
        }

        return content.toString();
    }

}
