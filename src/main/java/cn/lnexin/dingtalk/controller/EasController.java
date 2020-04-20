package cn.lnexin.dingtalk.controller;

import cn.lnexin.dingtalk.constant.Constant;
import cn.lnexin.dingtalk.entity.UserMapper;
import cn.lnexin.dingtalk.service.IEasService;
import cn.lnexin.dingtalk.utils.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;


@Controller
@RequestMapping(value = "/eas")
public class EasController {
    private static final Logger bizLogger = LoggerFactory.getLogger(EasController.class);
    @Autowired
    IEasService iEasService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @GetMapping(value = "/getUserNumber")
    @ResponseBody
    public HashMap<String,Object> getUserNumber(@RequestParam("token") String token){
        HashMap<String,Object> result = new LinkedHashMap<>();
        result.put("status",0);//成功
        result.put("message",EncryptUtil.getInstance().Base64Decode(token));//成功
        return  result;
    }

    /**
     * 跳转登录页
     * @param userId
     * @return
     */
    @RequestMapping("/gotoLogin")
    public String gotoLogin(String userId){
       // request.getSession().setAttribute("userId",userId);
        session.setAttribute("userId",userId);
        return "login";
    }

    /**
     * 判断该用户是否已经绑定EAS用户名
     * @return
     */
    @GetMapping(value = "/getData")
    @ResponseBody
    public HashMap<String,Object> getData(){
        HashMap<String,Object> result = new LinkedHashMap<>();
        //String userId = (String)request.getSession().getAttribute("userId");
        String userId = (String)session.getAttribute("userId");
        result.put("data",userId);
        return result;
    }
    /**
     * 判断该用户是否已经绑定EAS用户名
     * @param userId
     * @return
     */
    @GetMapping(value = "/isMapper")
    @ResponseBody
    public HashMap<String,Object> isUserMapper(@RequestParam("userId") String userId){
        HashMap<String,Object> result = new LinkedHashMap<>();
        if (iEasService.isUserMapper(userId)){
            //已经关联，直接跳转流程助手首页
            result.put("status","success");//成功
            System.out.println("账户信息已经关联，直接跳转流程助手首页");
        }else {
            result.put("status","fail");//失败
            //未关联跳转登录页
            System.out.println("未关联跳转登录页");
        }
        return result;
    }



    /**
     * EAS登录接口验证
     * @return
     */
    @PostMapping(value = "/doEasLogin")
    @ResponseBody
    public boolean doEasLogin(@RequestParam("username") String username ,@RequestParam("password") String password){
        return iEasService.easLogin(username,password,Constant.slnName,Constant.dcName,Constant.language,Constant.dbType);
    }


    /**
     * 绑定钉钉userid到关联表
     * @return
     */
    @PostMapping(value = "/doUserMapper")
    @ResponseBody
    public boolean doUserMapper(@RequestParam("userId") String userId ,@RequestParam("usernumber") String usernumber){
        return iEasService.doUserMapper(userId,usernumber);
    }

   @GetMapping("/getUserNumberByUserId")
   @ResponseBody
    public String selectUserNumberByUserId(@RequestParam("userId") String userId){
       UserMapper user =  new UserMapper();
       user.setUSERID(userId);
       UserMapper result = iEasService.selectUserNumberByUserId(user);
      return result.getUSERNUMBER();
   }
}
