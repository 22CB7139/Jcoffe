package com.sorry.utils.bcelencode;

import com.sorry.utils.bytesandfiles.TransforBytes;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Base64;

/**
 * created by 0x22cb7139 on 2021/6/23
 */
public class bcelencode {
    public static void main(String[] args) throws IOException {
        String p1 = new BASE64Encoder().encode(TransforBytes.BytesFromFile("/Users/0x22cb7139/programing/java/jcoffe/utils/target/classes/com/sorry/utils/custom/WeaverXstream.class"));
        String k2 = new BASE64Encoder().encode(TransforBytes.BytesFromFile("/Users/0x22cb7139/programing/java/jcoffe/utils/target/classes/com/sorry/utils/custom/Filtershell.class"));
        System.out.println(p1+"\n\n\n\n\n\n"+k2);
    }
}
