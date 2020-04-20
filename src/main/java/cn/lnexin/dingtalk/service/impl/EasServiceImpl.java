package cn.lnexin.dingtalk.service.impl;

import cn.lnexin.dingtalk.client.WSContext;
import cn.lnexin.dingtalk.dao.UserDao;
import cn.lnexin.dingtalk.entity.UserMapper;
import cn.lnexin.dingtalk.service.IEasService;
import cn.lnexin.dingtalk.webservice.CallWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EAS服务端接口实现类
 */
@Service
public class EasServiceImpl implements IEasService {

    @Autowired
    private UserDao userDao;
    @Override
    public boolean easLogin(String userName, String password, String slnName, String dcName, String language, int dbType) {
        boolean flag = false;
        WSContext ctx  = CallWebService.getInstance().doEasLoginNew(userName, password, slnName, dcName, language, dbType);
        if (ctx.getSessionId()!=null){
            flag=true;
        }
        return  flag;
    }
    public boolean isUserMapper(String userid){
        boolean flag = false;
        UserMapper user = new UserMapper();
        user.setUSERID(userid);
        UserMapper resultMap = userDao.selectByUserId(user);
        if(resultMap!=null){
            flag=true;
        }
        return flag;
    }

    /**
     *绑定用户名
     * @param userid
     * @param usernumber
     * @return
     */
    public boolean doUserMapper(String userid,String usernumber){
        boolean flag = false;
        UserMapper user = new UserMapper();
        user.setUSERID(userid);
        user.setUSERNUMBER(usernumber);
        int a = userDao.insert(user);
        if (a==1){
            flag = true;
        }
        return flag;
    }

    @Override
    public UserMapper selectUserNumberByUserId(UserMapper record) {
        return userDao.selectByUserId(record);
    }
}

