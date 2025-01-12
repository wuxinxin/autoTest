package com.example.dztest.utils;

import com.alibaba.fastjson.JSON;
import com.example.dztest.domain.HttpDeleteWithBody;
import com.example.dztest.domain.HttpRequest;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.domain.HttpType;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static HttpResponse request(String methodName, String server_url, HttpType httpType, String path, Map<String, Object> header_map, Map<String, Object> param_map,
                                       Map<String, Object> data_map, String body, List<File> files) throws UnsupportedEncodingException {
        String encode = "utf-8";
        String url = server_url + path;
        StringBuffer param = new StringBuffer();
        int i = 0;
        if (param_map != null && param_map.size() > 0) {
            for (String key : param_map.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(param_map.get(key).toString());
                i++;
            }
        }
        url += param.toString();
        HttpResponse response = null;
        switch (httpType) {
            case GET:
                response = httpGet(url, header_map, encode);
                break;
            case POST:
                //如果是登陆接口，去掉请求头中的Authorization
                if ("login" == methodName) {
                    header_map.remove("Authorization");
                }

                if (header_map.containsKey("Content-Type") && header_map.get("Content-Type").equals("application/json")) {
                    if (body == null || body.isEmpty()) {
                        body = JSON.toJSONString(data_map);
                    }
                    response = httpPostRaw(url, body, header_map, encode);
                } else if (files.size() != 0) {
                    data_map.putAll(header_map);
                    System.out.println("in httpPostFormMultipart data_map = " + data_map);
                    response = httpPostFormMultipart(url, data_map, files, header_map, encode);
                } else {
                    response = httpPostForm(url, data_map, header_map, encode);
                }
                break;
            case PUT:
                response = httpPutRaw(url, body, header_map, encode);
                break;
            case DELETE:
                response = httpDelete(url, body, header_map, encode);
                break;
            default:
                throw new RuntimeException(String.format("暂不支持%s请求类型", httpType));
        }
        return response;
    }


    public static HttpResponse getResponse(String methodName, HttpRequest requestInfo) throws UnsupportedEncodingException {
        logger.info(requestInfo.toString());
        System.out.println("方法 " + methodName + " 请求入参: " + requestInfo.toString());
        HttpResponse response = request(methodName, requestInfo.getServer_url(), requestInfo.getType(), requestInfo.getPath(), requestInfo.getHeaders(),
                requestInfo.getParams(), requestInfo.getDatas(), requestInfo.getBody(), requestInfo.getFiles());
        logger.info(response.toString());
        return response;
    }

    /**
     * 发送http get请求
     */
    public static HttpResponse httpGet(String url, Map<String, Object> headers, String encode) {
        HttpResponse response = new HttpResponse();
        if (encode == null) {
            encode = "utf-8";
        }
        String content = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        // 忽略https证书验证
        closeableHttpClient = (CloseableHttpClient) wrapClient(closeableHttpClient);

        HttpGet httpGet = new HttpGet(url);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                if (entry.getValue() != null) {
                    httpGet.setHeader(entry.getKey(), entry.getValue().toString());
                } else {
                    httpGet.setHeader(entry.getKey(), null);
                }

            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发送 http post 请求，参数以form表单键值对的形式提交。
     */
    public static HttpResponse httpPostForm(String url, Map<String, Object> params, Map<String, Object> headers, String encode) {
        HttpResponse response = new HttpResponse();
        if (encode == null) {
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        // 忽略https证书验证
        closeableHttpClient = (CloseableHttpClient) wrapClient(closeableHttpClient);

        HttpPost httpost = new HttpPost(url);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        //组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            Object object = httpResponse.getAllHeaders();
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * description 忽略https证书验证
     * @author yanzy
     * @version 1.0
     * @date 2021/9/8 14:42
     */
    public static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            X509TrustManager tm = new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpClients.createDefault();
        }
    }


    /**
     * 发送 http post 请求，参数以原生字符串进行提交
     *
     * @param url
     * @param encode
     * @return
     */
    public static HttpResponse httpPostRaw(String url, String stringJson, Map<String, Object> headers, String encode) {
        HttpResponse response = new HttpResponse();
        if (encode == null) {
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        // 忽略https证书验证
        closeableHttpClient = (CloseableHttpClient) wrapClient(closeableHttpClient);

        HttpPost httpost = new HttpPost(url);

        //设置header
        httpost.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        //组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpost.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发送 http put 请求，参数以原生字符串进行提交
     *
     * @param url
     * @param encode
     * @return
     */
    public static HttpResponse httpPutRaw(String url, String stringJson, Map<String, Object> headers, String encode) {
        HttpResponse response = new HttpResponse();
        if (encode == null) {
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        // 忽略https证书验证
        closeableHttpClient = (CloseableHttpClient) wrapClient(closeableHttpClient);

        HttpPut httpput = new HttpPut(url);

        //设置header
        httpput.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpput.setHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        //组织请求参数
        if (stringJson == null) {
            logger.info("stringJson is null");
        } else {
            StringEntity stringEntity = new StringEntity(stringJson, encode);
            httpput.setEntity(stringEntity);
        }

        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpput);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();  //关闭连接、释放资源
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发送http delete请求
     */
    public static HttpResponse httpDelete(String url, String stringJson, Map<String, Object> headers, String encode) throws UnsupportedEncodingException {
        HttpResponse response = new HttpResponse();
        if (encode == null) {
            encode = "utf-8";
        }
        String content = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        // 忽略https证书验证
        closeableHttpClient = (CloseableHttpClient) wrapClient(closeableHttpClient);

//        HttpDelete httpdelete = new HttpDelete(url);
        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
        //设置body
        if (stringJson != null && stringJson.length() != 0) {
            httpDelete.setEntity(new StringEntity(stringJson));
            ;
        }

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpDelete.setHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpDelete);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {   //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发送 http post 请求，支持文件上传
     */
    public static HttpResponse httpPostFormMultipart(String url, Map<String, Object> params, List<File> files, Map<String, Object> headers, String encode) {

        HttpResponse response = new HttpResponse();
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        if (encode == null) {
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        // 忽略https证书验证
        closeableHttpClient = (CloseableHttpClient) wrapClient(closeableHttpClient);

        HttpPost httpost = new HttpPost(url);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mEntityBuilder.setCharset(Charset.forName(encode));

        // 普通参数
        ContentType contentType = ContentType.create("text/plain", Charset.forName(encode));//解决中文乱码
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                mEntityBuilder.addTextBody(key, params.get(key).toString(), contentType);
            }
        }
        //二进制参数
        if (files != null && files.size() > 0) {
            for (File file : files) {
                mEntityBuilder.addBinaryBody("file", file);
            }
        }

        httpost.setEntity(mEntityBuilder.build());
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
