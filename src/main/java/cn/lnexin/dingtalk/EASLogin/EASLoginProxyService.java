/**
 * EASLoginProxyService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.lnexin.dingtalk.EASLogin;

public interface EASLoginProxyService extends javax.xml.rpc.Service {
    public String getEASLoginAddress();

    public cn.lnexin.dingtalk.EASLogin.EASLoginProxy getEASLogin() throws javax.xml.rpc.ServiceException;

    public cn.lnexin.dingtalk.EASLogin.EASLoginProxy getEASLogin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
