package com.example.dztest.domain;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.Header;

import java.util.Arrays;

public class HttpResponse {
    private int statusCode;
    private String Body;
    private Header[] headers;
    private String reasonPhrase;

    public String getHeader(String key) {
        Header[] headerArray = this.getHeaders();
        String headerValue = null;
        for (Header header : headerArray) {
//            System.out.println("--Header-----------------------------------------");
//            System.out.println("----Key: " + header.getName());
//            System.out.println("----RawValue: " + header.getValue());
//            HeaderElement[] headerElementArray = header.getElements();
            if (key.equals(header.getName())) {
                headerValue = header.getValue();
            }
//            for(HeaderElement headerElement : headerElementArray)
//            {
//                System.out.print("------Value: " + headerElement.getName());
//                if(headerElement.getValue() != null)
//                {
//                    System.out.println("  <-|->  " + headerElement.getValue());
//                }
//
//                NameValuePair[] nameValuePairArray = headerElement.getParameters();
//                for(NameValuePair nameValuePair : nameValuePairArray)
//                {
//                    if(key.equals(nameValuePair.getName())){
//                        headerValue =  headerElement.getValue();
//                    }
//
//                    System.out.println("------Parameter: " + nameValuePair.getName() + "  <-|->  " + nameValuePair.getValue());
//                }
//            }
        }
        return headerValue;

    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statuscode) {
        this.statusCode = statuscode;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode=" + statusCode +
                ", Body='" + Body + '\'' +
                ", headers=" + Arrays.toString(headers) +
                ", reasonPhrase='" + reasonPhrase + '\'' +
                '}';
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public <T> T body(String jsonPath) {
        JSONObject body = JSONObject.parseObject(getBody());
        return JsonPath.read(body, jsonPath);
    }
}
