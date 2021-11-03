package com.sorry.bug.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * created by 0x22cb7139 on 2021/4/16
 */
public class MyRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username=(String) token.getPrincipal();
        if(!username.equals("sorry")){
            throw new UnknownAccountException("账户不存在");
        }else{
            return new SimpleAuthenticationInfo(username,"123456",getName());
        }


    }
}