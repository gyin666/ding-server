package cn.lnexin.dingtalk.EASLogin;

public class EASLoginProxyProxy implements cn.lnexin.dingtalk.EASLogin.EASLoginProxy {
  private String _endpoint = null;
  private cn.lnexin.dingtalk.EASLogin.EASLoginProxy eASLoginProxy = null;
  
  public EASLoginProxyProxy() {
    _initEASLoginProxyProxy();
  }
  
  public EASLoginProxyProxy(String endpoint) {
    _endpoint = endpoint;
    _initEASLoginProxyProxy();
  }
  
  private void _initEASLoginProxyProxy() {
    try {
      eASLoginProxy = (new cn.lnexin.dingtalk.EASLogin.EASLoginProxyServiceLocator()).getEASLogin();
      if (eASLoginProxy != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)eASLoginProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)eASLoginProxy)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (eASLoginProxy != null)
      ((javax.xml.rpc.Stub)eASLoginProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cn.lnexin.dingtalk.EASLogin.EASLoginProxy getEASLoginProxy() {
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy;
  }
  
  public boolean logout(String userName, String slnName, String dcName, String language) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.logout(userName, slnName, dcName, language);
  }
  
  public cn.lnexin.dingtalk.client.WSContext login(String userName, String password, String slnName, String dcName, String language, int dbType) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.login(userName, password, slnName, dcName, language, dbType);
  }
  
  public cn.lnexin.dingtalk.client.WSContext login(String userName, String password, String slnName, String dcName, String language, int dbType, String authPattern) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.login(userName, password, slnName, dcName, language, dbType, authPattern);
  }
  
  public cn.lnexin.dingtalk.client.WSContext login(String userName, String password, String slnName, String dcName, String language, int dbType, String authPattern, int isEncodePwd) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.login(userName, password, slnName, dcName, language, dbType, authPattern, isEncodePwd);
  }
  
  public cn.lnexin.dingtalk.client.WSContext loginByLtpaToken(String userName, String ltpaToken, String slnName, String dcName, String language, int dbType) throws java.rmi.RemoteException{
    if (eASLoginProxy == null)
      _initEASLoginProxyProxy();
    return eASLoginProxy.loginByLtpaToken(userName, ltpaToken, slnName, dcName, language, dbType);
  }
  
  
}