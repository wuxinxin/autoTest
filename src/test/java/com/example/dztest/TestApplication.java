package com.example.dztest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;

/**
 * Test启动类
 * 【勿删，不可删除】
 *
 * @ClassName: TestApplication
 * @description: 由于spring对src/main和src/test机制引起的在src/test中不能@Autowire问题添加启动类在此
 * @author: jian.ma@msxf.com
 * @create: 2021/12/17
 **/

@EnableFeignClients
@SpringBootApplication
@MapperScan("com.example.dztest.extentreport.db.dao")
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
        double estimateCallNum = 0.6;
        int callNum = (int) estimateCallNum;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:com/example/dztest/service/interfaces/**/*.class");
            for (Resource res : resources) {
                System.err.println(res.getURI());
                String clsName = new SimpleMetadataReaderFactory().getMetadataReader(res).getClassMetadata().getClassName();
                System.out.println(clsName);
                try {
                    System.err.println(Class.forName(clsName).getName());
                } catch (Exception ex) {
                    System.out.println("Exception " + ex);
                }

            }
        } catch (IOException io) {
            System.out.println("IOException " + io);
        }

    }

}
