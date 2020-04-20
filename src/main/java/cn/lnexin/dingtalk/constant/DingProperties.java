package cn.lnexin.dingtalk.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 钉钉相关变量
 *
 * @author lnexin@foxmail.com
 * @Description TODO
 **/
@Configuration
public class DingProperties {

    /**
     * 套件ID现在没用上
     */
    @Value("${ding.suite.id}")
    private String suiteId;

    /**
     * 套件Key
     */
    @Value("${ding.suite.key}")
    private String suiteKey;

    /**
     * 套件secret
     */
    @Value("${ding.suite.secret}")
    private String suiteSecret;

    /**
     * 套件token
     * 在微应用的基础信息中我们自定义的那个token
     */
    @Value("${ding.suite.token}")
    private String suiteToken;

    /**
     * 套件.数据加密密钥.用于回调的解密
     * 在微应用的基础信息中"数据加密密钥"
     */
    @Value("${ding.suite.aes-key}")
    private String encodingAESKey;

    /**
     * 服务器推送的设置ticket,测试随便填写
     */
    @Value("${ding.suite.ticket}")
    private String suiteTicket;

    /**
     * 根据 suiteKey, suiteSecret, suiteTicket
     * 获取第三方应用凭证 suite_access_token
     */
    public static final String url_suite_token = "https://oapi.dingtalk.com/service/get_suite_token";
    /**
     * 根据临时授权码 tem_code, suite_access_token
     * 获取永久授权码 permanent_code
     */
    public static final String url_permanent_code = "https://oapi.dingtalk.com/service/get_permanent_code";

    /**
     * 根据 suite_access_token, suiteKey, corpId, permanentCode
     * 激活应用 的企业授权
     */
    public static final String url_activate_suite = "https://oapi.dingtalk.com/service/activate_suite";

    /**
     * 根据suiteKey, suiteSecret, suiteTicket, corpId
     * 获取企业授权信息
     */
    public static final String url_get_auth_info = "https://oapi.dingtalk.com/service/get_auth_info";


    /**
     * 根据suiteKey, suiteSecret, suiteTicket, corpId
     * 获取企业授权凭证
     */
    public static final String url_get_access_token = "https://oapi.dingtalk.com/service/get_corp_token";


    /**
     * 根据免登授权码code, access_token
     * 获取用户的userid
     */
    public static final String url_get_user_id = "https://oapi.dingtalk.com/user/getuserinfo";
    /**
     * 根据access_token, userid
     * 获取用户详情
     */
    public static final String url_get_user_item = "https://oapi.dingtalk.com/user/get";


    public String getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getSuiteSecret() {
        return suiteSecret;
    }

    public void setSuiteSecret(String suiteSecret) {
        this.suiteSecret = suiteSecret;
    }

    public String getSuiteToken() {
        return suiteToken;
    }

    public void setSuiteToken(String suiteToken) {
        this.suiteToken = suiteToken;
    }

    public String getEncodingAESKey() {
        return encodingAESKey;
    }

    public void setEncodingAESKey(String encodingAESKey) {
        this.encodingAESKey = encodingAESKey;
    }

    public String getSuiteTicket() {
        return suiteTicket;
    }

    public void setSuiteTicket(String suiteTicket) {
        this.suiteTicket = suiteTicket;
    }

    @Override
    public String toString() {
        return "DingProperties{" +
                "suiteId='" + suiteId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteSecret='" + suiteSecret + '\'' +
                ", suiteToken='" + suiteToken + '\'' +
                ", encodingAESKey='" + encodingAESKey + '\'' +
                ", suiteTicket='" + suiteTicket + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                " 'suiteId':'" + suiteId + '\'' +
                ", 'suiteKey':'" + suiteKey + '\'' +
                ", 'suiteSecret':'" + suiteSecret + '\'' +
                ", 'suiteToken':'" + suiteToken + '\'' +
                ", 'encodingAESKey':'" + encodingAESKey + '\'' +
                ", 'suiteTicket':'" + suiteTicket + '\'' +
                ", 'url_suite_token':'" + url_suite_token + '\'' +
                ", 'url_permanent_code':'" + url_permanent_code + '\'' +
                ", 'url_activate_suite':'" + url_activate_suite + '\'' +
                ", 'url_get_auth_info':'" + url_get_auth_info + '\'' +
                ", 'url_get_access_token':'" + url_get_access_token + '\'' +
                ", 'url_get_user_id':'" + url_get_user_id + '\'' +
                ", 'url_get_user_item':'" + url_get_user_item + '\'' +
                '}';
    }
}
