## `钉钉微应用` 服务端示例程序.
> `接入流程`/`使用说明`/`教程` 请参照: [https://www.cnblogs.com/applerosa/p/11509512.html](https://www.cnblogs.com/applerosa/p/11509512.html)  
>
> 适用于`第三方企业` 的 `H5微应用`, 消息处理模式为`HTTP回调`  

  <a href="https://img.shields.io/badge/JDK-1.8-green.svg?style=flat-square" title="JDK编译版本">
    <img alt="code style" src="https://img.shields.io/badge/JDK-1.8-green.svg?style=flat-square">
  </a>
  <a href="https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg" title="maven构建版本">
    <img alt="maven" src="https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg?style=flat-square">
  </a>  
  <a href="https://img.shields.io/badge/springboot-2.1.3.RELEASE-green.svg">
    <img alt="maven" src="https://img.shields.io/badge/springboot-2.1.3.RELEASE-green.svg?style=flat-square">
  </a>

## 编译/构建/打包
> 项目采用`maven` 构建/管理依赖/打包发布  
> 清理编译结果重新编译: `mvn clean compile`  
> 构建/打包: `mvn package`  

由于我自身在`pom.xml`中的相关配置， 所以`package`的结果，lib依赖包是独立的，没有放入最终的jar包，部署时需要注意

## 项目说明
1. 使用了钉钉`java`语言的`SDK jar 包`,位置在`/bak/toabao-xxxxxxxx.jar`, 请自行引入;
2. 钉钉微应用/套件 的配置在项目主配置文件`resources/application.yml`里面;
3. 项目使用的额外的一些工具类, `apache`的`commons-codec`中`Base64`, 除`springboot`自身依赖之外无其他依赖;
4. 项目导入之后, 编译完成, 启动 `cn.lnexin.DingApplication.main()` 即可启动项目;

## 功能说明
> 再强调一遍: 适用于`第三方企业` 的 `H5微应用`, 消息处理模式为`HTTP回调`   

因为是微应用服务端的示例程序,所以某些功能具体的业务实现是没有的,比如: 钉钉发送下单数据过来, 示例包含接收订单数据步骤, 处理逻辑各人自己补充;  

> 相关概念说明  

```$xslt
套件/微应用   suiteKey             : 创建微应用后,基础信息里面有
套件/微应用   suiteSecret          : 应用密钥, 创建微应用后,基础信息里面有
套件/微应用   suiteTicket          : 钉钉给应用推送的ticket，测试应用随意填写如：TestSuiteTicket，正式应用需要从推送回调获取suiteTicket
套件/微应用   suitetoken           : 钉钉推送消息过来,我们解密数据时需要, 创建微应用后,基础信息里面
套件/微应用   aesKey               : 消息加密解密用, 创建微应用后,基础信息里面
       
免登授权码    code                 : 前端在调用钉钉相关 jsapi "dd.runtime.permission.requestAuthCode()" 之后获得
企业授权凭证  accessToken          : 由后台根据suiteKey,suiteSecret,suitrTicket 向钉钉服务器请求获得
       
企业id        corpId              : 企业/组织的唯一Id
用户userid    userId              : 用户在当前企业内的唯一ID
用户unionid   unionId             : 用户在我们开发的整个微应用中, 的唯一ID

第三方应用凭证 suite_access_token : 根据我们的suitekey/secret/ticket 向钉钉请求获取的, 这个是我们的应用(套件)对购买了应用的企业授权用的
临时授权码     tmp_auth_code      : 钉钉发给我们的
永久授权码     permanentCode      : 根据 临时授权码 和 suite_access_token 获取的, 用于激活应用授权

```
> 下面大概说一下有哪些示例:  

1. 根据 corpId 获取 accessToken ;
2. 通过 免登code / accessToken  获取 用户的userId
3. 通过 accessToken / userId获取 用户详情(包括姓名)  
   (一般的登录流程为:  
     微应用进入前端页面获取企业ID,然后前端初始化获取免登code,   
     然后服务端用根据这两个参数, 通过上面3个步骤, 查询出户信息  
     补充自己的一些处理逻辑,将数据返回前端即可)    

4. 获取企业的授权详细信息,这里可以理解为通过 accessToken/corpID 获取企业详情;

5. 剩余的接口请参照 钉钉官方服务端开发文档 

> 回调功能相关(钉钉推送数据到我们服务器)  
  
因为是基于HTTP回调的模式,所以需要下面的步骤
1.  解密消息
2.  加密我们返回的消息;
3.  获取第三方应用凭证 suite_access_token , 这个是应用(套件)对企业授权用的, 和上面的accessToken不是一个东西;
3.  根据 suite_access_token / 临时授权码 获取 永久授权码;
4.  根据 suite_access_token / 永久授权码 / corpId 激活应用;  
    (完成上面的步骤,就可以授权企业使用应用或者体验应用,意思就是你就可以在钉钉里面看到这个微应用了)
    

## api接口
|功能|uri地址|类型|参数|返回|
|:---|:---|:---:|:---|:---|
|测试:获取配置|/ding/config |GET|无|测试服务是否启动成功|
|登录|/ding/login |GET|code,corpId|返回用户详情,里面包含token|
|获取accessToken|/ding/token |GET|corpId|仅仅只是获取accessToken|
|钉钉回调接口|/ding/callback |POST|signature,timestamp,nonce,encryptBody|前3个参数位于url中,encrtpt位于post的body中|


