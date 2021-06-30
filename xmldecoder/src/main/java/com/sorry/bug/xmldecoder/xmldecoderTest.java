package com.sorry.bug.xmldecoder;

import com.sorry.bug.Execute;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * created by 0x22cb7139 on 2021/6/8
 */
public class xmldecoderTest {


    public static void main(String[] args) throws Exception {

        String path = "xmldecoder/src/main/resources/Exploit.xml";
        try{
            new xmldecoderTest().parsexml(path);
        }catch (Exception e){
            e.printStackTrace();
        }
         /*

        Execute execute = new Execute("whoami");
        XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream("xmldecoder/src/main/resources/Exploit.xml"));
        xmlEncoder.writeObject(execute);
        xmlEncoder.close();
        */
    }

    private void parsexml(String path){
        try{
            XMLDecoder xd = new XMLDecoder(new FileInputStream(path));
            Object obj = xd.readObject();
            xd.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

