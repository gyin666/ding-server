package cn.lnexin.dingtalk.webservice;

import cn.lnexin.dingtalk.EASLogin.EASLoginProxyProxy;
import cn.lnexin.dingtalk.client.WSContext;

public class CallWebService {
    public static CallWebService callWebService;
    public static CallWebService getInstance(){
        if (callWebService==null) {
            synchronized (CallWebService.class) {
                if(callWebService == null){
                    callWebService = new CallWebService();
                }
            }
        }
        return callWebService;
    }
   /* public static String doEASloginService(String url,String userName,String password,String slnName,String language,String dcName,int dbType) {
        String result = "";
        try {
            String endpoint = url + "?wsdl";
            // 直接引用远程的wsdl文件
            // 以下都是套路
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("login");// WSDL里面描述的接口名称
            TypeMappingRegistry registry = service.getTypeMappingRegistry();
            TypeMapping mapping = registry.createTypeMapping();
            call.addParameter("userName",
                    XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("password",
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("slnName",
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("dcName",
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("language",
                    org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("dbType",
                    XMLType.XSD_INT,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            result = (String) call.invoke(new Object[] { userName,password,slnName,dcName,language,dbType});
            // 给方法传递参数，并且调用方法
            System.out.println("result is " + result);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return result;
    }*/
    public WSContext doEasLoginNew(String userName, String password, String slnName, String dcName, String language, int dbType){
        try {
            EASLoginProxyProxy proxy = new EASLoginProxyProxy();
            /**
             * web service客户端登陆
             * userName 用户名
             * password 密码
             * slnName eas
             * dcName 数据中心,对应的是dataCenter中数据中心id
             * language 语言 L2简体
             * dbType 数据库类型 0:sqlServer 1:db2 2:oracle
             * authPattern 验证方式 默认 "BaseDB" ; 其他认证方式KEY可从easAuthPatterns.xml中获取
             */
            WSContext ctx = proxy.login(userName, password, slnName, dcName, language, dbType);
            //成功登录后打印sessionId
            System.out.println("userName-------------------"+userName);
            System.out.println("password-------------------"+password);
            System.out.println("slnName-------------------"+slnName);
            System.out.println("返回结果-------------------"+ctx.getSessionId());
            //调用凭证业务webservice方法importVoucher（凭证引入），请看1.55节
            return ctx;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
