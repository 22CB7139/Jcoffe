package com.sorry.bug;

import java.io.*;

/**
 * created by 0x22cb7139 on 2021/4/15
 */

public class ExecuteSerialize {
    public static void main(String[] args) throws IOException,ClassNotFoundException {
        Serialize("EXP.ser");
        UnSerialize("EXP.ser");
        return;
    }

    public static void Serialize(String targetname) throws IOException {
        Execute execute = new Execute();
        execute.setCommand("id");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(targetname)));
        oos.writeObject(execute);
        oos.close();
    }

    public static void UnSerialize(String targetname) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(targetname)));
        ois.readObject();
        ois.close();
    }
}

