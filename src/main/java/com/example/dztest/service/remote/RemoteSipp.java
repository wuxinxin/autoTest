package com.example.dztest.service.remote;

import ch.ethz.ssh2.Connection;
import com.example.dztest.service.feign.SippFeignService;
import com.example.dztest.utils.SpringContextUtil;
import com.example.dztest.utils.SshUtil;
import com.example.dztest.utils.uilogger.UILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RemoteSipp {
    @Autowired
    private SippFeignService sippFeignService;

    @Value("${server.sipp.url}")
    private String sipp_url;

    @Value("${server.sipp.port}")
    private Integer sipp_port;

    @Value("${server.sipp.user}")
    private String sipp_user;

    @Value("${server.sipp.pwd}")
    private String sipp_pwd;

    @Value("${sipp.model}")
    private String sipp_model;

    private Connection connection;

    @UILogger(desc = "建立sipp远程连接")
    public void connection() {
        if ("ssh".equals(sipp_model)) {
            connection = SshUtil.openConnection(sipp_url, sipp_port, sipp_user, sipp_pwd, null);
        }
    }

    //呼出场景调用，注册手机号码
    @UILogger(desc = "启动sipp注册")
    public void runOutboundSipp() {

        //生产:sass/大客户不需要注册sipp
        if (this.isNeedSippReg()) {
            if ("ssh".equals(sipp_model)) {
                String cmd_str = "cd /home/icc;sh outbound_robot.sh";
                SshUtil.executeWithResult(connection, cmd_str);
            } else {
                sippFeignService.sippOutboundRegister();
            }
        }

    }

    private boolean isNeedSippReg() {
        String active = SpringContextUtil.getActiveProfile();
        boolean flag = true;

        if ("sass".equals(active)) {
            flag = false;
        }

        if ("vip".equals(active)) {
            flag = false;
        }

        return flag;
    }

    //呼入场景调用，呼入inBoundPhone
    public void runInboundSipp(String proxyServerIpPort, String inBoundPhone) {
        String cmd_str = "/home/icc/sipp/sipp-3.3/sipp " + proxyServerIpPort + " -r 2 -rp 1000 -i " + this.sipp_url + " -p 50021 -s "
                + inBoundPhone +
                " -sf /home/icc/sipp/sipp-3.3/dezhutest/inbound_G711a.xml -inf /home/icc/sipp/sipp-3.3/dezhutest/CS2.csv -m 1 -trace_msg -trace_screen -trace_err -aa";
        System.out.println("呼入执行cmd = " + cmd_str);
        if ("ssh".equals(sipp_model)) {
            SshUtil.executeWithoutResult(connection, cmd_str);
        } else {
            sippFeignService.sippInbound(inBoundPhone);
        }

    }
}
