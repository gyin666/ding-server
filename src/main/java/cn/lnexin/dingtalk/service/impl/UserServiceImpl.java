package cn.lnexin.dingtalk.service.impl;

import cn.lnexin.dingtalk.constant.DingProperties;
import cn.lnexin.dingtalk.entity.User;
import cn.lnexin.dingtalk.service.IUserService;
import cn.lnexin.dingtalk.utils.Strings;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 钉钉用户信息 服务实现类
 * </p>
 *
 * @author lnexin@aliyun.com
 * @since 2019-09-10
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    DingProperties dingProperties;


    @Override
    public String getUserId(String code, String accessToken) {
        DingTalkClient client = new DefaultDingTalkClient(DingProperties.url_get_user_id);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");
        String userId = "";
        try {
            OapiUserGetuserinfoResponse response = client.execute(request, accessToken);
            userId = response.getUserid();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return userId;
    }


    @Override
    public User getUserItem(String accessToken, String userId) {
        DingTalkClient client = new DefaultDingTalkClient(DingProperties.url_get_user_item);
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        User user = null;
        try {
            OapiUserGetResponse response = client.execute(request, accessToken);
            if (!Strings.isNullOrEmpty(response.getBody())) {
                response.getUserid();
                user = new User(response.getBody());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return user;
    }

}
