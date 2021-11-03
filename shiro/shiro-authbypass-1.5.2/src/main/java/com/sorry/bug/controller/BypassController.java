package com.sorry.bug.controller;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by 0x22cb7139 on 2021/4/16
 */
@RestController
public class BypassController {
    /*
    过滤器配置（参考ShiroConfig）中bypass映射认证过滤器最后一个URI字符没有/，使用spring和shiro对资源的解析不一致进行bypass
    */
    @RequestMapping(value = "/bypass", method = RequestMethod.GET)
    public String bypass() {
        return "bypass";
    }
}