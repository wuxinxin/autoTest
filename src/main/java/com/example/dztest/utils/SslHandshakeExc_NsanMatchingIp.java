package com.example.dztest.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.URL;

public class SslHandshakeExc_NsanMatchingIp{

    /**
     * 下载
     * @param url_path 下载文件路径
     */
    public static String download(String url_path) throws Exception {
        // 获取连接
        URL url = new URL(url_path);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 新增部分
        conn.setHostnameVerifier(new SslHandshakeExc_NsanMatchingIp().new TrustAnyHostnameVerifier());

        // 设置请求方法
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Charset", "UTF-8");

        // 获取文件名
        String base_path = SslHandshakeExc_NsanMatchingIp.class.getResource("/").getPath();
        String distDir = base_path.replaceFirst("test-classes", "classes") + "static/tmp/";
        String filePath = distDir + "/" + TimeUtil.getNowTime("yyyyMMddHHMMSS") + ".wav";

        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        // 获取输入流，并写入文件
        try (InputStream inputStream = conn.getInputStream();
             OutputStream os = new FileOutputStream(file)) {
            byte[] buffer = new byte[256];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.flush();
        }
        return filePath;
    }

    /**
     * 下载
     * @param url_path 下载文件路径
     */
    public static String download(String url_path, String  filename, String taskID) throws Exception {
        // 获取连接
        URL url = new URL(url_path);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 新增部分
        conn.setHostnameVerifier(new SslHandshakeExc_NsanMatchingIp().new TrustAnyHostnameVerifier());

        // 设置请求方法
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Charset", "UTF-8");

        // 获取文件名
        String base_path = SslHandshakeExc_NsanMatchingIp.class.getResource("/").getPath();
        String distDir = base_path.replaceFirst("test-classes", "classes") + "static/tmp/" + taskID;
        String filePath = distDir + "/" + filename + ".wav";

        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        // 获取输入流，并写入文件
        try (InputStream inputStream = conn.getInputStream();
             OutputStream os = new FileOutputStream(file)) {
            byte[] buffer = new byte[256];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.flush();
        }
        return filePath;
    }

    // 定制Verifier
    public class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}