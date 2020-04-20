package cn.lnexin.dingtalk.constant;

/**
 * 钉钉回调相关事件类型
 *
 * @author lnexin
 */
public class CallbackConstant {
    /**
     * 回调时成功的字符串
     */
    public static final String CALLBACK_RETURN_SUCCESS = "success";
    /**
     * 激活失败返回
     */
    public static final String ACTIVE_RETURN_FAILURE = "active_failure";

    /**
     * 验证回调地址是否有效
     */
    public static final String SUITE_TICKET_CALLBACK_URL_VALIDATE = "suite_ticket";
    /**
     * 在开发者后台修改套件时如果回调地址有变化会推送该事件
     */
    public static final String CHECK_UPDATE_SUITE_URL = "check_update_suite_url";
    /**
     * 钉钉向回调URL POST数据, 解密后是否成功
     */
    public static final String CHECK_CREATE_SUITE_URL = "check_create_suite_url";

    /**
     * 临时授权码,授权开通
     */
    public static final String TEMP_AUTH_CODE_ACTIVE = "tmp_auth_code";
    /**
     * 解除授权事件
     */
    public static final String SUITE_RELIEVE = "suite_relieve";

    /**
     * 停用应用
     */
    public static final String ORG_MICRO_APP_STOP = "org_micro_app_stop";
    /**
     * 启用应用
     */
    public static final String ORG_MICRO_APP_RESTORE = "org_micro_app_restore";

    /**
     * 授权方（即授权企业）在钉钉手机客户端-微应用管理中，修改了对应用的授权企业通讯录范围
     * 授权变更信息并不包括企业用户具体做了什么修改，所以收到推送之后，ISV需要通过调用“获取通讯录权限接口”查询新的授权范围。。
     */
    public static final String CONTACT_CHANGE_AUTH = "change_auth";

    /**
     * 用户下单购买事件
     */
    public static final String MARKET_BUY = "market_buy";


}
