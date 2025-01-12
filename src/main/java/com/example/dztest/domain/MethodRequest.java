package com.example.dztest.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dztest.common.annotations.*;
import com.example.dztest.utils.JsonUtil;
import com.example.dztest.utils.SpringContextUtil;
import com.example.dztest.utils.uilogger.UILogger;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodRequest {
    @Value("${gateway.url}")
    private String server_url;

    //从接口类注解获取
    private String zuul_path;
    private String url;

    //从接口方法注解获取
    private String path;
    private HttpType httpType;

    //从接口方法参数注解获取
    private String tag_str;

    //入参数据
    private Map<String, Object> param_map = new HashMap<>();
    //    private Map<String, Object> body_map = new HashMap<>();
    private Map<String, Object> body_raw_map = new HashMap<>();
    private Map<String, Object> body_formdata_map = new HashMap<>();
    private Map<String, Object> header_map = new HashMap<>();
    private Map<String, Object> pathVariable_map = new HashMap<>();

    //注解数据
    private Class<?> interface_class;
    //获取接口上的注解@Resource
    private Annotation[] interface_annotations;
    //获取方法上的注解,比如@Post等
    private Annotation[] method_annotations;
    //获取参数的注解
    private Annotation[][] param_annotations;
    //得到类名称
    private String class_name;
    //得到方法名称
    private String method_name;

    //获取接口文件绝对路径
    private String filePath;
    //获取当前环境
    private String env;
    //拼接接口文件对应的json数据文件
    private String jsonfile;

    //入参
    private Object[] args;

    //解析后的http request数据
    private HttpRequest httpRequest = new HttpRequest();

    //是否是文件操作
    Boolean fileOperate = false;

    public MethodRequest(Method method, Object[] method_args) {
        interface_class = method.getDeclaringClass();
        interface_annotations = interface_class.getAnnotations();
        method_annotations = method.getAnnotations();
        param_annotations = method.getParameterAnnotations();
        class_name = interface_class.getSimpleName();
        method_name = method.getName();

//        filePath = interface_class.getResource("").getPath();

        String class_all_name = interface_class.getName();
        this.filePath = class_all_name.replace(".", "/").replaceFirst(class_name, "");
//        this.filePath = interface_class.getResource("").getPath();
        this.env = SpringContextUtil.getActiveProfile();
//        InputStream inputStream = MethodRequest.class.getClassLoader().getResourceAsStream("com/example/dztest/service/interfaces/basic/data/default/BasicRoleControllerApi.json");

        env = SpringContextUtil.getActiveProfile();
        this.jsonfile = this.filePath + "data" + "/" + env + "/" + class_name + ".json";

        args = method_args;

        this.parseClassAnnotations();
        this.parseMethodAnnotations();
        this.parseParamAnnotations();


        this.parseHttpRequest();
    }



    //环境对应的json文件>default的json文件
    public JSONObject getJsonObject() {
        //环境对应的json文件
        String json_file = this.filePath + "data" + "/" + env + "/" + class_name + ".json";
        System.out.println("json_file: " + json_file );
//        File file = new File(json_file);
        try {
            JSONObject obj = parserJsonFileS(json_file);
            if (obj != null) {
                return obj;
            }
        } catch (Exception exception) {
            System.out.println("file is no exist: " + json_file);
        }

        //环境对应的json文件
        String json_file_default = this.filePath + "data" + "/" + "default" + "/" + class_name + ".json";
        System.out.println("json_file: " + json_file_default );
        //        File file_default = new File(json_file_default);
        try {
            JSONObject obj = parserJsonFileS(json_file_default);
            if (obj != null) {
                return obj;
            }
        } catch (Exception exception) {
            System.out.println("file is no exist: " + json_file);
        }

        System.out.println("json_file_default is null "  );
        return null;
    }

    //从json文件中获取匹配的json对象
    public JSONObject parserJsonFileS(String json_file) {
        //解析json文件
//        String json_str = JsonUtil.readJsonFile(json_file);
        String json_str = null;
        try {
            json_str = JsonUtil.readWithClassLoader(json_file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(json_str);
        JSONObject obj = JSON.parseObject(json_str);

        Object obj_tmp = obj.get(method_name);
        JSONObject obj_method = null;
        if (obj_tmp instanceof JSONObject) {
            obj_method = (JSONObject) obj_tmp;
        } else if (obj_tmp instanceof JSONArray) {
            //如果找不到匹配的tag，则默认取第一个
            obj_method = ((JSONArray) obj.get(method_name)).getJSONObject(0);

            for (int i = 0; i < ((JSONArray) obj_tmp).size(); i++) {
                JSONObject obj_t = ((JSONArray) obj_tmp).getJSONObject(i);
                if (obj_t.get("tag").toString().equals(tag_str)) {
                    obj_method = obj_t;
                    break;
                }
            }
        } else {
            //没有找到方法名称对应的json数据，则返回null
            obj_method = null;
        }
        return obj_method;
    }

    //从json文件中获取匹配的json对象
    public JSONObject parserJsonFile(String json_file) {
        //解析json文件
        String json_str = JsonUtil.readJsonFile(json_file);
//        String json_str = JsonUtil.readWithClassLoader(json_file);

        JSONObject obj = JSON.parseObject(json_str);

        Object obj_tmp = obj.get(method_name);
        JSONObject obj_method = null;
        if (obj_tmp instanceof JSONObject) {
            obj_method = (JSONObject) obj_tmp;
        } else if (obj_tmp instanceof JSONArray) {
            //如果找不到匹配的tag，则默认取第一个
            obj_method = ((JSONArray) obj.get(method_name)).getJSONObject(0);

            for (int i = 0; i < ((JSONArray) obj_tmp).size(); i++) {
                JSONObject obj_t = ((JSONArray) obj_tmp).getJSONObject(i);
                if (obj_t.get("tag").toString().equals(tag_str)) {
                    obj_method = obj_t;
                    break;
                }
            }
        } else {
            //没有找到方法名称对应的json数据，则返回null
            obj_method = null;
        }
        return obj_method;
    }

    //解析得到的http request
    public void parseHttpRequest() {
        JSONObject jsonObject = this.getJsonObject();
        //如果在json文件中找到method对应的json数据
        if (jsonObject != null) {
            JSONObject obj_header = jsonObject.getJSONObject("header");
            if (obj_header != null) {
                //先put json数据文件中的header数据
                for (Map.Entry<String, Object> entry : obj_header.entrySet()) {
                    httpRequest.getHeaders().put(entry.getKey(), entry.getValue());
                }
            }

            JSONObject obj_query = jsonObject.getJSONObject("param");
            if (obj_query != null) {
                //先put json数据文件中的param数据
                for (Map.Entry<String, Object> entry : obj_query.entrySet()) {
                    httpRequest.getParams().put(entry.getKey(), entry.getValue());
                }
            }

            //处理body
            //得到body部分的json数据
            String mode = "raw";
            JSONObject obj_body_t = jsonObject.getJSONObject("body");
            if (obj_body_t != null) {
                JSONObject obj_body = null;
                if (obj_body_t.containsKey("mode") && obj_body_t.getString("mode").equals("raw")) {
                    //如果 body数据中，包含mode的key，并且mode的value值等于raw
                    obj_body = obj_body_t.getJSONObject("raw");
                } else if (obj_body_t.containsKey("mode") && obj_body_t.getString("mode").equals("form-data")) {
                    //如果 body数据中，包含mode的key，并且mode的value值等于form-data
                    obj_body = obj_body_t.getJSONObject("form-data");
                    mode = "form-data";
                } else {
                    //默认为raw数据
                    obj_body = obj_body_t;
                }

                if (mode.equals("raw")) {
                    String body = obj_body.toJSONString();
                    for (Map.Entry<String, Object> entry : body_raw_map.entrySet()) {
                        //json字符串处理：替换字段的值
                        body = JsonPath.parse(body).set(entry.getKey(), entry.getValue()).jsonString();
                    }
                    httpRequest.setBody(body);

                } else if (mode.equals("form-data")) {
                    //put json数据文件中的formdata数据
                    for (Map.Entry<String, Object> entry : obj_body.entrySet()) {
                        httpRequest.getDatas().put(entry.getKey(), entry.getValue());
                    }
                    ;
                } else {
                    throw new RuntimeException(String.format("[%s]m模式不匹配", mode));
                }
            }
        }

        //putall入参header
        httpRequest.getHeaders().putAll(header_map);
        //putall入参param
        httpRequest.getParams().putAll(param_map);
        //putall入参formdata
        httpRequest.getDatas().putAll(body_formdata_map);

        httpRequest.setType(httpType);

        //接口类上注解url = admin则为A端请求，否则为B端请求
        if ("admin".equals(this.url)) {
            if (GlobalVar.A_GLOBAL_DATA_MAP.get("Authorization") != null) {
                //如果Authorization不为空，说明登录过
                httpRequest.getHeaders().put("Authorization", GlobalVar.A_GLOBAL_DATA_MAP.get("Authorization"));
            }
        } else {
            if (GlobalVar.GLOBAL_DATA_MAP.get("Authorization") != null) {
                //如果Authorization不为空，说明登录过
                httpRequest.getHeaders().put("Authorization", GlobalVar.GLOBAL_DATA_MAP.get("Authorization"));
            }
        }

        //如果没有Content-Type，则默认为application/json
        if (!httpRequest.getHeaders().containsKey("Content-Type") && !fileOperate) {
            httpRequest.getHeaders().put("Content-Type", "application/json");
        }
    }

    //解析接口类上的注解
    public void parseClassAnnotations() {
        for (Annotation annotation : interface_annotations) {
            if (annotation instanceof Mapping) {
                zuul_path = ((Mapping) annotation).path();
                url = ((Mapping) annotation).url();
                break;
            }
        }
    }

    //解析接口方法上的注解
    public void parseMethodAnnotations() {
        for (Annotation annotation : method_annotations) {
            //放行自定义注解
            if (annotation instanceof UILogger) {
                continue;
            }

            if (annotation instanceof Post) {
                path = ((Post) annotation).path();
                httpType = HttpType.POST;
            } else if (annotation instanceof Get) {
                path = ((Get) annotation).path();
                httpType = HttpType.GET;
            } else if (annotation instanceof Delete) {
                path = ((Delete) annotation).path();
                httpType = HttpType.DELETE;
            } else if (annotation instanceof Put) {
                path = ((Put) annotation).path();
                httpType = HttpType.PUT;
            } else {
                throw new RuntimeException(String.format("[%s]--[%s]方法上不支持配置的注解[%s]",
                        class_name, method_name, annotation.annotationType()));
            }
        }
        httpRequest.setPath(zuul_path + path);
    }

    //解析接口参数上的注解
    public void parseParamAnnotations() {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < param_annotations.length; i++) {
            Annotation[] annotations = param_annotations[i];
            if (annotations.length != 0) {
                if (annotations[0] instanceof Header) {
                    header_map.put(((Header) annotations[0]).value(), args[i]);
                } else if (annotations[0] instanceof Param) {
                    param_map.put(((Param) annotations[0]).value(), args[i]);
                } else if (annotations[0] instanceof Raw) {
                    //后续统一处理同一个参数在多个json key的情况下，只需要显示声明入参一次，所有的参数注解提供通用处理 TODO
                    System.out.println("处理Raw注解参数 + " + ((Raw) annotations[0]).value());
                    body_raw_map.put(((Raw) annotations[0]).value(), args[i]);
                } else if (annotations[0] instanceof FormData) {
                    body_formdata_map.put(((FormData) annotations[0]).value(), args[i]);
                } else if (annotations[0] instanceof PathVariable) {
                    if (args[i] != null) {
                        String request_path = path.replaceFirst("(\\w|/+)(\\{\\w+})(\\w|/?)", "$1" + args[i] + "$3");
                        httpRequest.setPath(zuul_path + request_path);
                    }
                } else if (annotations[0] instanceof FilePath) {
                    fileOperate = true;
                    File file = new File(args[i].toString());
                    files.add(file);
                } else if (annotations[0] instanceof tag) {
                    tag_str = args[i].toString();
                } else {
                    throw new RuntimeException(String.format("方法[%s]中配置的参数注解不支持[%s]", method_name, annotations[0].annotationType()));
                }
            }
        }
        httpRequest.setFiles(files);

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public String getMethod_name() {
        return method_name;
    }

    public String getZuul_path() {
        return zuul_path;
    }
}
