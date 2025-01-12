package com.example.dztest.utils.screen_shot;

import com.example.dztest.utils.OverScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @ClassName: MContextListener
 * @description: screen listener
 * @author: jian.ma@msxf.com
 * @create: 2022/07/13
 **/

@Deprecated
@Configuration
public class MContextListener implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(MContextListener.class);

    @Autowired
    OverScreen overScreen;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextClosedEvent) {
            logger.info("Ioc listener ...");
            //overScreen.commonScreen();
        }
    }
}
