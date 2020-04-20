package cn.lnexin.dingtalk.controller;

import cn.lnexin.dingtalk.service.IDingAuthService;
import cn.lnexin.dingtalk.service.ISuiteCallbackService;
import cn.lnexin.dingtalk.utils.JsonTool;
import cn.lnexin.dingtalk.utils.Strings;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static cn.lnexin.dingtalk.constant.CallbackConstant.*;

/**
 * [钉钉] - 钉钉的回调接口, 包含开通,授权,启用,停用,下单等
 *
 * @author work.lnexin@foxmail.com
 * @Description TODO
 **/
@RestController
@RequestMapping("/call")
public class SuiteCallbackController {
    static Logger logger = LoggerFactory.getLogger(SuiteCallbackController.class);


    /**
     * 钉钉发过来的数据格式:
     * <p>
     * http://您服务端部署的IP:您的端口/callback?signature=111108bb8e6dbce3c9671d6fdb69d15066227608&timestamp=1783610513&nonce=380320111
     * 包含的json数据为：
     * {
     * "encrypt":"1ojQf0NSvw2WPvW7LijxS8UvISr8pdDP+rXpPbcLGOmIBNbWetRg7IP0vdhVgkVwSoZBJeQwY2zhROsJq/HJ+q6tp1qhl9L1+ccC9ZjKs1wV5bmA9NoAWQiZ+7MpzQVq+j74rJQljdVyBdI/dGOvsnBSCxCVW0ISWX0vn9lYTuuHSoaxwCGylH9xRhYHL9bRDskBc7bO0FseHQQasdfghjkl"
     * }
     */


    @Autowired
    ISuiteCallbackService suiteCallbackService;

    @Autowired
    IDingAuthService dingAuthService;

    /**
     * 钉钉服务器推送消息 的地址
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encryptNode
     * @return
     */
    @PostMapping(value = "/callback")
    public Map<String, String> tempAuthCodeCallback(@RequestParam String signature,
                                                    @RequestParam String timestamp,
                                                    @RequestParam String nonce,
                                                    @RequestBody JsonNode encryptNode) {
        String encryptMsg = encryptNode.get("encrypt").textValue();
        String plainText = suiteCallbackService.decryptText(signature, timestamp, nonce, encryptMsg);
        JsonNode plainNode = JsonTool.getNode(plainText);

        //进入回调事件分支选择
        Map<String, String> resultMap = caseProcess(plainNode);
        return resultMap;
    }

    /**
     * 根据回调数据类型做不同的业务处理
     *
     * @param plainNode
     * @return
     */
    private Map<String, String> caseProcess(JsonNode plainNode) {
        Map<String, String> resultMap = new LinkedHashMap<>();
        String eventType = plainNode.get("EventType").textValue();
        switch (eventType) {
            case SUITE_TICKET_CALLBACK_URL_VALIDATE:
                logger.info("[callback] 验证回调地址有效性质:{}", plainNode);
                resultMap = suiteCallbackService.encryptText(CALLBACK_RETURN_SUCCESS);
                break;
            case TEMP_AUTH_CODE_ACTIVE:
                logger.info("[callback] 企业开通授权:{}", plainNode);
                Boolean active = suiteActive(plainNode);
                resultMap = suiteCallbackService.encryptText(active ? CALLBACK_RETURN_SUCCESS : ACTIVE_RETURN_FAILURE);
                break;
            case SUITE_RELIEVE:
                logger.info("[callback] 企业解除授权:{}", plainNode);
                suiteRelieve(plainNode);
                resultMap = suiteCallbackService.encryptText(CALLBACK_RETURN_SUCCESS);
                break;
            case CHECK_UPDATE_SUITE_URL:
                logger.info("[callback] 在开发者后台修改回调地址:" + plainNode);
                resultMap = suiteCallbackService.encryptText(CALLBACK_RETURN_SUCCESS);
                break;
            case CHECK_CREATE_SUITE_URL:
                logger.info("[callback] 检查钉钉向回调URL POST数据解密后是否成功:" + plainNode);
                resultMap = suiteCallbackService.encryptText(CALLBACK_RETURN_SUCCESS);
                break;
            case CONTACT_CHANGE_AUTH:
                logger.info("[callback] 通讯录授权范围变更事件:" + plainNode);
                break;
            case ORG_MICRO_APP_STOP:
                logger.info("[callback] 停用应用:" + plainNode);
                break;
            case ORG_MICRO_APP_RESTORE:
                logger.info("[callback] 启用应用:" + plainNode);
                break;
            default:
                logger.info("[callback] 未知事件: {} , 内容: {}", eventType, plainNode);
                resultMap = suiteCallbackService.encryptText("事件类型未定义, 请联系应用提供方!" + eventType);
                break;
        }
        return resultMap;
    }

    /**
     * 激活应用授权
     * tmp_auth_code
     */
    private Boolean suiteActive(JsonNode activeNode) {
        Boolean isActive = false;
        String corpId = activeNode.get("AuthCorpId").textValue();
        String tempAuthCode = activeNode.get("AuthCode").textValue();

        String suiteToken = suiteCallbackService.getSuiteToken();
        String permanentCode = suiteCallbackService.getPermanentCode(suiteToken, tempAuthCode);
        if (!Strings.isNullOrEmpty(permanentCode)) {
            isActive = suiteCallbackService.activateSuite(suiteToken, corpId, permanentCode);
            if (isActive) {
                getCorpAuthInfo(corpId);
            }
        } else {
            logger.error("获取永久授权码出错");
        }
        return isActive;
    }

    /**
     * 获取企业授权信息
     *
     * @param corpId
     */
    private void getCorpAuthInfo(String corpId) {
        String corpToken = dingAuthService.getAccessToken(corpId);
        logger.info("获取企业授权凭证:{}", corpId);
        JsonNode corpAuthInfo = dingAuthService.getCorpAuthInfo(corpId);
        logger.info("获取企业信息详情:{}", corpAuthInfo);

    }

    /**
     * 解除授权
     * suite_relieve
     */
    private void suiteRelieve(JsonNode plainNode) {
    }


}