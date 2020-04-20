package cn.lnexin.dingtalk.service;

import java.util.Map;

/**
 * 钉钉套件注册/绑定/解绑/认证相关流程
 *
 * @author lnexin@aliyun.com
 */
public interface ISuiteCallbackService {


    /**
     * 解密钉钉传输过来的密文
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encryptMsg
     * @return
     */
    String decryptText(String signature, String timestamp, String nonce, String encryptMsg);

    /**
     * 加密要发送的密文
     *
     * @param text
     * @return
     */
    Map<String, String> encryptText(String text);

    /**
     * 获取第三方应用凭证suite_access_token
     *
     * @return
     */
    String getSuiteToken();

    /**
     * 获取企业永久授权码
     *
     * @param suiteAccessToken 第三方应用凭证
     * @param tempCode         临时授权码
     * @return
     */
    String getPermanentCode(String suiteAccessToken, String tempCode);

    /**
     * 激活企业授权的应用
     *
     * @param suiteAccessToken 第三方应用凭证
     * @param corpId           需要激活的企业ID
     * @param permanentCode    永久授权码
     * @return
     */
    Boolean activateSuite(String suiteAccessToken, String corpId, String permanentCode);


}
