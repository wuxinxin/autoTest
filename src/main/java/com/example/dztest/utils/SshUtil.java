package com.example.dztest.utils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class SshUtil {

    private static String DEFAULT_CHART = "UTF-8";
    private static Long TIME_OUT = 60000L;
    private static Long WAIT_TIME = 20000L;
    private static final Logger LOGGER = LogManager.getLogger(SshUtil.class.getName());


    /**
     * 连接
     *
     * @param host 地址
     * @param port 端口
     * @param user 用户名
     * @param pwd 密码
     * @param keyChar 密钥
     * @return
     */
    public static Connection openConnection(String host, int port, String user, String pwd, char[] keyChar) {
        Connection conn = null;
        try {
            conn = new Connection(host, port);
            conn.connect();
            // 登录认证
            boolean flag;
            if (keyChar != null) {
//                密钥方式
                flag = conn.authenticateWithPublicKey(user, keyChar, pwd);
            } else {
//                账号密码方式
                flag = conn.authenticateWithPassword(user, pwd);
            }
            if (flag) {
                LOGGER.info("认证成功：success");
            } else {
                LOGGER.info("认证失败，断开连接");
                conn.close();
            }
        } catch (Exception e) {
            LOGGER.error("认证异常：{}", e.getMessage());
        }
        return conn;
    }


    /**
     * 等待结果
     *
     * @param connection
     * @param command
     * @return
     */
    public static String executeWithResult(Connection connection, String command) {
        LOGGER.info("执行shell指令:{}", command);
        Session session = null;
        String result = "";
        try {
//            打开一个会话
            session = connection.openSession();
//            执行命令
            session.execCommand(command);
            result = processStdout(session.getStdout());
            LOGGER.info("shell 执行结果: {}",result);
        } catch (IOException e) {
            LOGGER.error("执行指令：{}，异常：{}", command, e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
//            if (null != connection) {
//                connection.close();
//            }
        }
        return result;
    }

    /**
     * 处理执行结果
     *
     * @param in
     * @return
     */
    private static String processStdout(InputStream in) {
        InputStream stdout = new StreamGobbler(in);
        StringBuilder buffer = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            String line;
//            if (br.lines() != null) {
//                buffer.append("success").append("\n");
//            }
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                buffer.append(line);
            }
            br.close();
        } catch (IOException e) {
            LOGGER.error("解析脚本出错：{}", e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 只执行不等待结果
     *
     * @param connection
     * @param command
     */
    public static void executeWithoutResult(Connection connection, String command) {
        Session session = null;
        try {
            session = connection.openSession();
            LOGGER.info("执行shell指令：{}", command);
            // 1、建立虚拟终端
//            session.requestPTY("bash");
            session.requestDumbPTY();
            // 2、打开一个Shell
            session.startShell();
            // 3、准备输入命令
            PrintWriter out = new PrintWriter(session.getStdin());
            // 4、输入待执行命令
            out.println(command);
//            CommonUtil.sleep(20000L);
            out.println("exit");
            // 6. 关闭输入流
            out.close();
            // 7. 等待，除非-1.连接关闭；2.输出数据传送完毕；3.进程状态为退出；4.超时
            session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 30000);
        } catch (IOException e) {
            LOGGER.error("执行异常:{}", e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
//            if (connection != null) {
//                connection.close();
//            }
        }
    }


    /**
     * 执行shell脚本
     *
     * @param bashCommand
     */
    public void executeBashCommand(String bashCommand) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入IP:");
            String ip = reader.readLine();
            Runtime runtime = Runtime.getRuntime();
            Process pro = runtime.exec(bashCommand);
            int status = pro.waitFor();
            if (status != 0) {
                LOGGER.info("Failed to call shell's command:{}", bashCommand);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuilder strbr = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                strbr.append(line).append("\n");
            }
            String result = strbr.toString();
            LOGGER.info("执行结果：{}", result);
        } catch (IOException | InterruptedException ec) {
            ec.printStackTrace();
        }
    }

}
