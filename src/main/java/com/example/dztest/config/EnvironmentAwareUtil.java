package com.example.dztest.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class EnvironmentAwareUtil implements EnvironmentAware {
    public static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public static String getVByKey(String key) {

        return environment.getProperty(key);
    }
}
