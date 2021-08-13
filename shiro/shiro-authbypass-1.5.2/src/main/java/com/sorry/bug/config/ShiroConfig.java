package com.sorry.bug.config;

import com.sorry.bug.realm.MyRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.springframework.web.util.UrlPathHelper;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * created by 0x22cb7139 on 2021/4/16
 */
@Configuration
public class ShiroConfig {
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
    @Bean
    MyRealm myRealm(){
        return new MyRealm();
    }

    @Bean
    SecurityManager securityManager() {

        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm());
        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }

    //*匹配零或多个字符
    //**匹配路径
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorizedurl");
        Map<String, String> map = new LinkedHashMap();
        map.put("/login", "anon");
        map.put("/aaaaa/**", "anon");
        map.put("/bypass", "authc");
        //map.put("/bypass.*", "authc");
        map.put("/bypass/**", "authc");
        //map.put("/**", "authc");
        bean.setFilterChainDefinitionMap(map);

        return bean;
    }
}
