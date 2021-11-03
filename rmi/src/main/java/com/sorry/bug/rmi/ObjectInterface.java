package com.sorry.bug.rmi;

/**
 * created by 0x22cb7139 on 2021/4/15
 */
import com.sorry.bug.Execute;

import java.io.IOException;
import java.rmi.Remote;

public interface ObjectInterface extends Remote {
    //public String Function(EXP exp) throws IOException,RemoteException;
    Execute Function(String cmd) throws IOException;
}
