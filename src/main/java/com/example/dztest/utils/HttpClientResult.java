package com.example.dztest.utils;

import java.io.Serializable;

public class HttpClientResult implements Serializable {
    /**
     * 响应状态码
     */
    private int code;

    private String header;

    /**
     * 响应数据
     */
    private String content;

    //   构造方法
    HttpClientResult() {

    }

    HttpClientResult(int code) {
        this.code = code;
    }


    HttpClientResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

    HttpClientResult(int code, String header, String content) {
        this.code = code;
        this.header = header;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}