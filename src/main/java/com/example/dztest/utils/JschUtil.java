package com.example.dztest.utils;

import com.jcraft.jsch.*;
import java.io.InputStream;
import java.io.IOException;

public class JschUtil {
    private static JSch jsch;
    private static Session session;

    public static void connectSippServer(String user, String password, String host, int port) throws JSchException{
        jsch = new JSch();
        session = jsch.getSession(user, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(60000);
    }

    public static void runScript() throws JSchException, IOException{
        String killSipp = "ps -ef | grep sipp | grep -v grep | awk -F ' ' '{print $2}' | xargs kill -9 >/dev/null 2>&1";
        String runOutboundScript = "sh /home/icc/sipp/outbound.sh";
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(killSipp + ";" + runOutboundScript);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);
        InputStream in = channel.getInputStream();
        channel.connect();

        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) continue;
                System.out.println("exit status: " + channel.getExitStatus());
                break;
            }
        }
    }

    public static void sessionDisconnect(){
        session.disconnect();
    }


}
