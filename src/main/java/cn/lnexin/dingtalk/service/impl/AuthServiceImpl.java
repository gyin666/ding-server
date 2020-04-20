package cn.lnexin.dingtalk.service.impl;

import cn.lnexin.dingtalk.constant.DingProperties;
import cn.lnexin.dingtalk.service.IDingAuthService;
import cn.lnexin.dingtalk.utils.JsonTool;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiServiceGetAuthInfoRequest;
import com.dingtalk.api.request.OapiServiceGetCorpTokenRequest;
import com.dingtalk.api.response.OapiServiceGetAuthInfoResponse;
import com.dingtalk.api.response.OapiServiceGetCorpTokenResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author work.lnexin@foxmail.com
 **/
@Service
public class AuthServiceImpl implements IDingAuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    DingProperties dingProperties;

    /**
     * {
     * "access_token":"xxxxxx",
     * "expires_in":7200
     * }
     */
    @Override
    public String getAccessToken(String corpId) {
        Map<String, Object> result = this.getAccessTokenMap(corpId);
        return String.valueOf(result.get("accessToken"));
    }

    @Override
    public Map<String, Object> getAccessTokenMap(String corpId) {
        DefaultDingTalkClient client = new DefaultDingTalkClient(DingProperties.url_get_access_token);
        OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
        req.setAuthCorpid(corpId);
        OapiServiceGetCorpTokenResponse execute = null;
        try {
            execute = client.execute(req, dingProperties.getSuiteKey(), dingProperties.getSuiteSecret(), dingProperties.getSuiteTicket());
        } catch (ApiException e) {
            logger.error("企业授权 - 获取凭证 出错, dingProperties: {}, corpId: {}, code: {}, msg: {}", dingProperties, corpId, e.getErrCode(), e.getErrMsg());
            e.printStackTrace();
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("accessToken", execute.getAccessToken());
        result.put("expiresIn", execute.getExpiresIn());
        logger.debug("企业授权 - 获取凭证, corpId: {}, result: {}", corpId, result);
        return result;
    }

    @Override
    public JsonNode getCorpAuthInfo(String corpId) {
        DingTalkClient client = new DefaultDingTalkClient(DingProperties.url_get_auth_info);
        OapiServiceGetAuthInfoRequest req = new OapiServiceGetAuthInfoRequest();
        req.setAuthCorpid(corpId);
        JsonNode result = JsonTool.emptyNode();
        try {
            OapiServiceGetAuthInfoResponse response = client.execute(req, dingProperties.getSuiteKey(), dingProperties.getSuiteSecret(), dingProperties.getSuiteTicket());
            result = JsonTool.getNode(response);
        } catch (ApiException e) {
            logger.error("获取企业授权-详情 出错, corpId: {}, code: {}, msg: {}", corpId, e.getErrCode(), e.getErrMsg());
        }
        logger.debug("获取企业授权-详情, corpId: {}, auth_result: {}", corpId, result);
        return result;
    }

    /**
     * 企业授权信息详情的返回结果
     * {
     *     "auth_corp_info": {
     *         "corp_logo_url": "http://xxxx.png",
     *         "corp_name": "corpid",
     *         "corpid": "auth_corpid_value",
     *         "industry": "互联网",
     *         "invite_code": "1001",
     *         "license_code": "xxxxx",
     *         "auth_channel": "xxxxx",
     *         "auth_channel_type": "xxxxx",
     *         "is_authenticated": false,
     *         "auth_level": 0,
     *         "invite_url": "https://yfm.dingtalk.com/invite/index?code=xxxx",
     *         "corp_province": "浙江",
     *         "corp_city": "杭州"
     *     },
     *     "auth_user_info": {
     *         "userId": ""
     *     },
     *     "auth_info": {
     *         "agent": [{
     *                 "agent_name": "aaaa",
     *                 "agentid": 1,
     *                 "appid": -3,
     *                 "logo_url": "http://aaaaaa.com",
     *                 "admin_list": [
     *                     "zhangsan",
     *                     "lisi"
     *                 ]
     *             }, {
     *                 "agent_name": "bbbb",
     *                 "agentid": 4,
     *                 "appid": -2,
     *                 "logo_url": "http://vvvvvv.com",
     *                 "admin_list": []
     *             }
     *         ]
     *     },
     *     "channel_auth_info": {
     *         "channelAgent": [{
     *                 "agent_name": "应用1",
     *                 "agentid": 36,
     *                 "appid": 6,
     *                 "logo_url": "http://i01.lw.test.aliimg.com/media/lALOAFWTc8zIzMg_200_200.png"
     *             }, {
     *                 "agent_name": "应用2",
     *                 "agentid": 35,
     *                 "appid": 7,
     *                 "logo_url": "http://i01.lw.test.aliimg.com/media/lALOAFWTc8zIzMg_200_200.png"
     *             }
     *         ]
     *     },
     *     "errcode": 0,
     *     "errmsg": "ok"
     * }
     */
}
