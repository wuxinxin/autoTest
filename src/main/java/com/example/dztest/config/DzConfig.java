package com.example.dztest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 配置类
 * @author xinxin.wu
 * @date 2024/02/22
 * @version: 1.0
 */

@Configuration
public class DzConfig {

    /**
     * 登录账号,配置文件中设置
     */
    public static String account;

    /**
     * 用例结果输出类型；执行参数中设置;默认为html；mysql--用例结果保存到mysql;feishu--用例结果保存到飞书
     */
    public static String out;

    /**
     * 被测系统版本，如V2.4.3
     */
    public static String version;

    /**
     * 测试用例执行环境
     */
    public static String env;

    /**
     * 测试脚本分支
     */
    public static String branch;

    /**
     * jenkins构建url;jenkins全局变量{BUILD_URL}
     */
    public static String buildUrl;

    @Value("${login.account}")
    public void setAccount(String param) {
        account = param;
    }

    @Value("${out:html}")
    public void setOut(String param) {
        out = param;
    }

    @Value("${version:default}")
    public void setVersion(String param) {
        version = param;
    }

    @Value("${spring.profiles.active:default}")
    public void setEnv(String param) {
        env = param;
    }

    @Value("${branch:default}")
    public void setBranch(String param) {
        branch = param;
    }

    @Value("${build.url:}")
    public void setBuildUrl(String param) {
        buildUrl = param;
    }

}
