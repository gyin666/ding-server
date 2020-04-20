package cn.lnexin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * 钉钉示例项目启动入口
 * <p>
 * 继承SpringBootServletInitializer类为了解决springboot打成war包，部署tomcat后访问404问题
 * @author lnexin@aliyun.com
 */
@SpringBootApplication
/*@EnableAutoConfiguration*/
@ComponentScan(basePackages = {"cn.lnexin.dingtalk.*"})
@MapperScan({"cn.lnexin.dingtalk.dao"})
public class DingApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DingApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(DingApplication.class);
    }

}
