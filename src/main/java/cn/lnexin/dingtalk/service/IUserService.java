package cn.lnexin.dingtalk.service;

import cn.lnexin.dingtalk.entity.User;

/**
 * <p>
 * 钉钉用户信息 服务类
 * </p>
 *
 * @author lnexin@aliyun.com
 * @since 2019-09-10
 */
public interface IUserService {

    /**
     * 通过免登授权码和access_token获取用户的userid
     *
     * @param code        免登授权码
     * @param accessToken 调用接口凭证
     * @return
     */
    String getUserId(String code, String accessToken);


    /**
     * 获取用户详情
     *
     * @param accessToken 调用接口凭证
     * @param userId      员工id
     * @return
     */
    User getUserItem(String accessToken, String userId);

}
