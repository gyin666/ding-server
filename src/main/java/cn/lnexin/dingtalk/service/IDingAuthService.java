package cn.lnexin.dingtalk.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

/**
 * 钉钉获取认证信息
 *
 * @author lnexin@aliyun.com
 */
public interface IDingAuthService {

    /**
     * 获取企业的授权 凭证 corp token
     *
     * @param corpId 企业corpId
     * @return
     */
    String getAccessToken(String corpId);

    /**
     * 获取企业的授权凭证和过期时间
     * @param corpId
     * @return
     */
    Map<String, Object> getAccessTokenMap(String corpId);
    /**
     * 获取企业授权信息
     *
     * @param corpId 企业corpId
     * @return
     */
    JsonNode getCorpAuthInfo(String corpId);

}
