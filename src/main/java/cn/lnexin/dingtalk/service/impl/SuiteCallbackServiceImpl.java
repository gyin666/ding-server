package cn.lnexin.dingtalk.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiServiceActivateSuiteRequest;
import com.dingtalk.api.request.OapiServiceGetPermanentCodeRequest;
import com.dingtalk.api.request.OapiServiceGetSuiteTokenRequest;
import com.dingtalk.api.response.OapiServiceActivateSuiteResponse;
import com.dingtalk.api.response.OapiServiceGetPermanentCodeResponse;
import com.dingtalk.api.response.OapiServiceGetSuiteTokenResponse;
import com.taobao.api.ApiException;
import cn.lnexin.dingtalk.constant.DingProperties;
import cn.lnexin.dingtalk.encrypt.DingTalkEncryptException;
import cn.lnexin.dingtalk.encrypt.DingTalkEncryptor;
import cn.lnexin.dingtalk.encrypt.Utils;
import cn.lnexin.dingtalk.service.ISuiteCallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 主要完成钉钉回调相关的一些功能
 * @author lnexin@foxmail.com
 * @Description TODO
 **/
@Service
public class SuiteCallbackServiceImpl implements ISuiteCallbackService {
    Logger logger = LoggerFactory.getLogger(SuiteCallbackServiceImpl.class);

    @Autowired
    DingProperties dingProperties;

    @Override
    public String decryptText(String signature, String timestamp, String nonce, String encryptMsg) {
        String plainText = "";
        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(dingProperties.getSuiteToken(), dingProperties.getEncodingAESKey(), dingProperties.getSuiteKey());
            plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
        } catch (DingTalkEncryptException e) {
            logger.error("钉钉消息体解密错误, signature: {}, timestamp: {}, nonce: {}, encryptMsg: {}, e: {}", signature, timestamp, nonce, encryptMsg, e);
        }
        logger.debug("钉钉消息体解密, signature: {}, timestamp: {}, nonce: {}, encryptMsg: {}, 解密结果: {}", signature, timestamp, nonce, encryptMsg, plainText);
        return plainText;
    }

    @Override
    public Map<String, String> encryptText(String text) {
        Map<String, String> resultMap = new LinkedHashMap<>();
        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(dingProperties.getSuiteToken(), dingProperties.getEncodingAESKey(), dingProperties.getSuiteKey());
            resultMap = dingTalkEncryptor.getEncryptedMap(text, System.currentTimeMillis(), Utils.getRandomStr(8));
        } catch (DingTalkEncryptException e) {
            logger.error("钉钉消息体加密,text: {}, e: {}", text, e);
        }
        logger.debug("钉钉消息体加密,text: {}, resultMap: {}", text, resultMap);
        return resultMap;
    }

    /**
     * {
     * "suite_access_token":"61W3mEpU66027wgNZ_MhGHNQDHnFATkDa9-2llqrMBjUwxRSNPbVsMmyD-yq8wZETSoE5NQgecigDrSHkPtIYA",
     * "expires_in":7200
     * }
     */
    @Override
    public String getSuiteToken() {
        DingTalkClient client = new DefaultDingTalkClient(DingProperties.url_suite_token);
        OapiServiceGetSuiteTokenRequest request = new OapiServiceGetSuiteTokenRequest();
        request.setSuiteKey(dingProperties.getSuiteKey());
        request.setSuiteSecret(dingProperties.getSuiteSecret());
        request.setSuiteTicket(dingProperties.getSuiteTicket());

        String accessToken = "";
        try {
            OapiServiceGetSuiteTokenResponse response = client.execute(request);
            accessToken = response != null ? response.getSuiteAccessToken() : "";
        } catch (ApiException e) {
            logger.error("获取第三方应用凭证suite_access_token出错, code: {}, msg: {}", e.getErrCode(), e.getErrMsg());
        }
        logger.debug("获取第三方应用凭证suite_access_token, accessToken:{}", accessToken);
        return accessToken;
    }

    /**
     * {
     * "permanent_code": "xxxx",
     * "auth_corp_info":
     * {
     * "corpid": "xxxx",
     * "corp_name": "name"
     * }
     * }
     */
    @Override
    public String getPermanentCode(String suiteAccessToken, String tempCode) {
        StringBuilder url = new StringBuilder();
        url.append(DingProperties.url_permanent_code);
        url.append("?suite_access_token=").append(suiteAccessToken);
        DingTalkClient client = new DefaultDingTalkClient(url.toString());
        OapiServiceGetPermanentCodeRequest req = new OapiServiceGetPermanentCodeRequest();
        req.setTmpAuthCode(tempCode);

        String permanentCode = "";
        try {
            OapiServiceGetPermanentCodeResponse rsp = client.execute(req);
            permanentCode = (rsp != null ? rsp.getPermanentCode() : "");
        } catch (ApiException e) {
            logger.error("获取永久授权码出错, tempCode: {}, code: {}, msg: {}", tempCode, e.getErrCode(), e.getErrMsg());
        }
        logger.debug("获取永久授权码, tempCode: {}, permanentCode: {}", tempCode, permanentCode);
        return permanentCode;
    }

    /**
     * 激活企业授权的应用
     * {
     * "errcode":0,
     * "errmsg":"ok"
     * }
     */
    @Override
    public Boolean activateSuite(String suiteAccessToken, String corpId, String permanentCode) {
        StringBuilder url = new StringBuilder();
        url.append(DingProperties.url_activate_suite);
        url.append("?suite_access_token=").append(suiteAccessToken);
        DingTalkClient client = new DefaultDingTalkClient(url.toString());

        OapiServiceActivateSuiteRequest req = new OapiServiceActivateSuiteRequest();
        req.setSuiteKey(dingProperties.getSuiteKey());
        req.setAuthCorpid(corpId);
        req.setPermanentCode(permanentCode);
        boolean isActive = false;
        try {
            OapiServiceActivateSuiteResponse rsp = client.execute(req);
            isActive = rsp.getErrmsg().equals("ok");
        } catch (ApiException e) {
            logger.error("激活应用的企业授权出错, corpId: {}, permanentCode: {}, code: {}, msg: {}", corpId, permanentCode, e.getErrCode(), e.getErrMsg());
        }
        logger.debug("激活应用的企业授权, corpId: {}, permanentCode: {}, isActive: {}", corpId, permanentCode, isActive);
        return isActive;
    }


}
