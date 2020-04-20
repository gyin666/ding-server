package cn.lnexin.dingtalk.service;

import cn.lnexin.dingtalk.entity.UserMapper;

/**
 * EAS服务端交互服务类
 */
public interface IEasService {

    /**
     * 登录校验
     * @param userName
     * @param password
     * @param slnName
     * @param dcName
     * @param language
     * @param dbType
     * @return
     */
    public boolean easLogin(String userName, String password, String slnName, String dcName, String language, int dbType);
    public boolean isUserMapper(String userid);
    public boolean doUserMapper(String userid, String usernumber);
    public UserMapper selectUserNumberByUserId(UserMapper record);
}
