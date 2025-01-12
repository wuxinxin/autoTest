package com.example.dztest.utils.common;

import com.example.dztest.config.BeanProcessorUtil;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.kms.RobotApi;
import com.example.dztest.utils.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RobotUtil {
    public static final Logger logger = LoggerFactory.getLogger(RobotUtil.class);

    @Autowired
    private RobotApi robotApi;

    public HttpResponse checkUtil(String taskId) {
        //检查导入进度
        HttpResponse httpResponse11;
        String progress = "";

        while (true) {
            HttpResponse response = robotApi.getRobotCopyProgress(taskId);
            progress = AssertUtil.getData(response, "$.data.progress").toString();
            Object failReason = AssertUtil.getData(response, "$.data.failReason");

            if (failReason != null) {
                httpResponse11 = response;
                break;
            }

            System.out.println("当前导入|复制进度progress为: " + progress + "%");

            if (progress.equals("100")) {
                httpResponse11 = response;
                break;
            }


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return httpResponse11;
    }

    //导入机器人
    public Map<String, String> importRobot(String robotName, String fileName, String taskId) {
        Map<String, String> map = new HashMap<>();

        String filePath = this.getFilePath(fileName);
        HttpResponse httpResponse = robotApi.importRobot(filePath, robotName, taskId);
        System.out.println("httpResponse = " + httpResponse);

        this.checkUtil(taskId);

        HttpResponse httpResponse1 = robotApi.queryForPage(robotName);
        System.out.println("httpResponse1 = " + httpResponse1);

        //从返回值提取机器人id和机器人robotId
        String id = AssertUtil.getData(httpResponse1, "$.data.list[0].id").toString();
        String robotId = AssertUtil.getData(httpResponse1, "$.data.list[0].robotId").toString();

        map.put("id", id);
        map.put("robotId", robotId);

        return map;
    }


    /**
     * 获取指定文件名的路径[需要带上文件名后缀]，文件放在resources/static下
     *
     * @param fileName
     * @return
     */
    public String getFilePath(String fileName) {
        String base_path = RobotUtil.class.getResource("/").getPath();
        String filePath = base_path.replaceFirst("test-classes", "classes") + "static/" + fileName;
        logger.info("机器人路径 = " + filePath);
        return filePath;
    }

    /**
     * 通过指定长度获取随机位数数字
     *
     * @return
     */
    public int getRandomDataByLen(int x) {
        return (int) ((Math.random() * 9 + 1) * this.power(10, (x - 1)));
    }

    private int power(int x, int n) {
        int result = 0;
        if (n == 0) {
            result = 1;
        } else {
            result = power(x, n / 2);
            result = result * result;
            if (n % 2 != 0) {
                result = x * result;
            }
        }
        return result;
    }
}
