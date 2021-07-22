package com.sorry.bug;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.reflect.Method;

/**
 * created by 0x22cb7139 on 2021/7/14
 */
public class Debug {
    public static void main(String[] args) {
        try{
            ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByExtension("js");
            scriptEngine.eval("java.lang.Runtime.getRuntime().exec('/System/Applications/Calculator.app/Contents/MacOS/Calculator');");

            Class clazz = Class.forName("javax.script.ScriptEngineManager");
            Object manager = clazz.getDeclaredConstructor().newInstance();
            Method method = clazz.getDeclaredMethod("getEngineByExtension", new Class[]{String.class});
            Object engine = method.invoke(manager,"js");
            Method eval = engine.getClass().getMethod("eval",new Class[]{String.class});
            eval.invoke(engine,"print('hello world2');");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
