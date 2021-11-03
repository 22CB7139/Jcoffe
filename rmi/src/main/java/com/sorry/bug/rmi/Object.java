package com.sorry.bug.rmi;

import com.sorry.bug.Execute;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * created by 0x22cb7139 on 2021/4/15
 */
public class Object extends UnicastRemoteObject implements ObjectInterface {
    protected Object() throws RemoteException {
        super();
    }

    /*
    public String Function(EXP exp) throws IOException,RemoteException{
        return exp.getClass().getName();
    }
    */


    public Execute Function(String cmd) throws IOException {
        Execute exp = new Execute();
        exp.setCommand(cmd);
        return exp;
    }


}
