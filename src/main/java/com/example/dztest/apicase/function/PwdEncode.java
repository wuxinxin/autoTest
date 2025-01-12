package com.example.dztest.apicase.function;

import com.example.dztest.utils.crypto.RSAUtilNew;

import static com.example.dztest.config.BeanProcessorUtil.logger;

/**
 * @description: 密码加密
 * @author xinxin.wu
 * @date 2023/12/29
 * @version: 1.0
 */
public class PwdEncode implements Function {

    @Override
    public String execute(String[] args) {
        String pwd = args[0];
        String pwdAES = null;
        try {
            pwdAES = RSAUtilNew.getEncodePwd(pwd);
        } catch (Exception ex) {
            logger.error("重置密码加密失败 {}", ex);
        }
        return pwdAES;
    }

    @Override
    public String getReferenceKey() {
        return "pwdEncode";
    }

}
