package com.sorry.bug;

import java.io.*;

/**
 * created by 0x22cb7139 on 2021/4/15
 */

public class Execute implements Serializable {
    private static final long serialVersionUID = 7439581476576889858L;
    public String command;
    static String sequence = "静态变量初始化";

    public Execute(){
        sequence = "无参构造函数";
        System.out.println(sequence);
    }
    {
        sequence = "代码块执行";
        System.out.println(sequence);
    }
    static{
        sequence = "静态代码块执行";
        System.out.println(sequence);
    }

    public Execute(String command) {
        this.command = command;
    }

    public String getCommand() throws IOException {
        return command;
    }

    public void setCommand(String command) throws IOException {
        this.command = command;
        System.out.println("set方法");
        System.out.println(Exploit(command));
    }


    //反序列化自动执行
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        //ois.defaultReadObject();
        System.out.println("unserialize--readObject()");
        System.out.println(Exploit(this.command));
    }

    //序列化自动执行
    private void writeObject(ObjectOutputStream oos) throws IOException{
        //oos.defaultWriteObject();
        System.out.println("serialize--writeObject()");
        System.out.println(Exploit(this.command));
    }


    //函数调用----Server执行并将结果发送给Client
    //Tips-如果返回的是一个恶意类呢?
    public String Exploit(String cmd) throws IOException {

        System.out.println("函数调用Exploit("+cmd+")");
        Process p;
        String result="";
        String disr;
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
            p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", cmd});
        } else {
            p = Runtime.getRuntime().exec(new String[]{"/bin/bash","-c",cmd});
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream(),"GB2312"));

        while((disr=bufferedReader.readLine())!=null){
            result += disr + "";
        }
        //return to client 结果返回给客户端
        return result;
    }
}

