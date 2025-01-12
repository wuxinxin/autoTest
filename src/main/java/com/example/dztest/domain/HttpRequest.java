package com.example.dztest.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    /**
     * 请求类型：post get
     */
    private HttpType type;

    //服务地址
    private String server_url;

    @Override
    public String toString() {
        return "HttpRequest{" +
                "url=" + server_url +
                ", type=" + type +
                ", path='" + path + '\'' +
                ", params=" + params +
                ", datas=" + datas +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                ", files=" + files +
                '}';
    }

    /**
     * 接口

     */
    private String path;
    /**
     * 请求携带的参数，key、value格式
     */
    private Map<String, Object> params = new HashMap<>();

    private Map<String, Object> datas = new HashMap<>();
    private Map<String, Object> headers = new HashMap<>();
    /**
     * 消息体
     */
    private String body;

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    /**

     * 上传的文件
     */
    private List<File> files = new ArrayList<>();

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public HttpType getType() {
        return type;
    }

    public void setType(HttpType type) {
        this.type = type;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String,Object> getDatas(){
        return this.datas;
    }

    public void setDatas(Map<String, Object> datas){
        this.datas = datas;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServer_url() {
        return server_url;
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }
}
