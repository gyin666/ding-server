package cn.lnexin.dingtalk.constant;

/**
 * 项目中的常量定义类
 */
public class Constant {
    /**
     * 企业corpid, 需要修改成开发者所在企业
     */
    public static final String CORP_ID = "dinga79ec4c06b77cdc235c2f4657eb6378f";//ding9c127f3f9d172ac3ee0f45d8e4f7c288
    /**
     * 应用的AppKey，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPKEY = "dinglk94sgmllq2g2iwr";//dingxhseqqvx8l8btizy
    /**
     * 应用的AppSecret，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPSECRET = "3nK-PgwW042zuBpW2H9FTVDfGs0vT60W3xFXUrb6G8-xVwx5XmoMuPFEL5ZklYyS";//P0WPYJYZ9Ic-puKoNNSdLEqDVGkETY-UosUh9uUkCxmXAgdVqCcnSqS3hYrxg2Qw

    /**
     * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成
     */
    public static final String ENCODING_AES_KEY = "m60387sv97e2kzb5mane7k78u67zealbm081izbh1ws";

    /**
     * 加解密需要用到的token，企业可以随机填写。如 "12345"
     */
    public static final String TOKEN = "UserToken";

    /**
     * 应用的agentdId，登录开发者后台可查看
     */
    public static final Long AGENTID = 711169998L;

    /**
     * 审批模板唯一标识，可以在审批管理后台找到
     */
    public static final String PROCESS_CODE = "***";

    /**
     * 回调host
     */
    public static final String CALLBACK_URL_HOST = "http://kingdee.ngrok2.xiaomiqiu.cn/callback/callbackNew";

    /**
     * EAS服务器IP地址
     */
    public static final String IP_ADDRESS = "61.161.248.68";

    public static final String PORT = "9000";

    public static final String userName = "user";//用户名

    public static final String password = "kingdee";//密码

    public static final String dcName = "zszkbf";//

    public static final String language = "L2";

    public static final String slnName = "eas";//eas固定的

    public static final int dbType = 2;
}
