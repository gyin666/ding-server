package cn.lnexin.dingtalk.controller;

import cn.lnexin.dingtalk.constant.DingProperties;
import cn.lnexin.dingtalk.constant.URLConstant;
import cn.lnexin.dingtalk.entity.User;
import cn.lnexin.dingtalk.entity.UserMapper;
import cn.lnexin.dingtalk.service.IDingAuthService;
import cn.lnexin.dingtalk.service.IEasService;
import cn.lnexin.dingtalk.service.IUserService;
import cn.lnexin.dingtalk.utils.AccessTokenUtil;
import cn.lnexin.dingtalk.utils.EncryptUtil;
import cn.lnexin.dingtalk.utils.JsonTool;
import cn.lnexin.dingtalk.utils.ServiceResult;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * [钉钉] - 1. 用户登陆, 用户信息获取
 *
 * @author work.lnexin@foxmail.com
 * @Description TODO
 **/
@RestController
@RequestMapping("/ding")
public class UserController {
    static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    IUserService dingUserService;

    @Autowired
    IDingAuthService dingAuthService;

    @Autowired
    DingProperties dingProperties;

    @Autowired
    IEasService iEasService;

    /**
     * 根据前台初始化后获取的免登授权码获取用户信息
     *
     * @param code   免登授权码
     * @param corpId 企业应用corpId
     * @return
     */
    @GetMapping("/login")
    @ResponseBody
    public Map<String,Object> authCodeLogin(@RequestParam("code") String code,
                                      @RequestParam("corpId") String corpId) {
        String corpAccessToken = dingAuthService.getAccessToken(corpId);
        String userId = dingUserService.getUserId(code, corpAccessToken);
      //  User userItem = dingUserService.getUserItem(corpAccessToken, userID);


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("code", code);
        result.put("token", corpAccessToken);
        result.put("userId", userId);

        logger.debug("[钉钉] 用户免登, 根据免登授权码code, corpId获取用户信息, code: {}, corpId:{}, result:{}", code, corpId, result);

        return result;
    }
    @GetMapping("/loginInner")
    @ResponseBody
    public  Map<String,Object>  login(@RequestParam(value = "authCode") String requestAuthCode) {
        //获取accessToken,注意正是代码要有异常流处理
        String accessToken = AccessTokenUtil.getToken();
        //获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(requestAuthCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        //3.查询得到当前用户的userId
        // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
        String userId = response.getUserid();
     //   String userName = getUserName(accessToken, userId);
    //    System.out.println(userName);
        //返回结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
      //  resultMap.put("userName", userName);
     //   ServiceResult serviceResult = ServiceResult.success(resultMap);
        return resultMap;
    }
    /**
     * 获取用户姓名
     *
     * @param accessToken
     * @param userId
     * @return
     */
    private String getUserName(String accessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_USER_GET);
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);
            return response.getName();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }




    /**
     * 跳转EAS流程助手主页
     * @param userId
     */
    @GetMapping("/gotoEas")
    @ResponseBody
    public Map<String,Object> gotoEasWorkflow(String userId) {
        UserMapper user = new UserMapper();
        user.setUSERID(userId);
        String usernumber = iEasService.selectUserNumberByUserId(user).getUSERNUMBER();
        StringBuffer sf = new StringBuffer();
        sf.append(URLConstant.URL_EAS_WORKFLOW);
        sf.append("&token=");
        sf.append(EncryptUtil.getInstance().Base64Encode(usernumber));
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("gotoURL",sf.toString());
        return result;
    }
    /**
     * 根据企业corpId获取token
     *
     * @param corpId 企业应用corpId
     * @return
     */
    @GetMapping("/token")
    public Map<String, Object> getAccessToken(@RequestParam("corpId") String corpId) {
        Map<String, Object> tokenMap = dingAuthService.getAccessTokenMap(corpId);
        logger.debug("[钉钉] 根据corpId 获取accessToken ,corpId:{}, accessToken:{}", corpId, tokenMap);
        return tokenMap;
    }

    @GetMapping("/config")
    public JsonNode getConfig() {
        return JsonTool.getNode(dingProperties.toJson());
    }

}
