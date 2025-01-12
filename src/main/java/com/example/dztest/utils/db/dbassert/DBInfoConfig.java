package com.example.dztest.utils.db.dbassert;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import cn.hutool.setting.dialect.Props;
import com.example.dztest.config.EnvironmentAwareUtil;
import com.example.dztest.utils.SpringContextUtil;
import com.example.dztest.utils.db.rollback.db.util.SystemUtil;
import com.example.dztest.utils.db.rollback.exception.VmOptionException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @ClassName: DBInfoConfig
 * @author: jian.ma@msxf.com
 * @create: 2022/6/7
 * @update: 2022/6/7
 **/

@SuppressWarnings(value = "all")
public class DBInfoConfig {
    private volatile static Props props;

    /**
     * 解决spring还没启动时拿不到env问题
     */
    static {
        try {
            String active = SystemUtil.getSystemProperties("Spring.profiles.active");

            if (null == active) {
                throw new VmOptionException("Vm Option 异常，请检查");
            }

            Setting setting = new Setting("db.setting", true);
            String byGroup = setting.getByGroup("config.prop.name", active);

            Properties properties = readProperties(byGroup);
            props = Props.getProp(properties.getProperty(ConfigConstants.CONFIG_PROP_NAME), CharsetUtil.UTF_8);
//            props = new Props(byGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO,优化后使用
        //load();
    }

    public static Properties readProperties(String propertiesName) throws IOException {
        //读取配置文件
        Properties properties = new Properties();
        properties.load(new InputStreamReader(DBInfoConfig.class.getClassLoader().getResourceAsStream(propertiesName), StandardCharsets.UTF_8));

        return properties;
    }

    private static void load() {
        /**
         * double check
         */
        if (props == null) {
            synchronized (DBInfoConfig.class) {
                if (props == null) {
                    props = Props.getProp(EnvironmentAwareUtil.getVByKey(ConfigConstants.CONFIG_PROP_NAME), CharsetUtil.UTF_8);
                }
            }
        }
    }

    public static String getStr(String key) {
        return props.getStr(key);
    }

    public static boolean containsKey(String key) {
        return props.containsKey(key);
    }
}
