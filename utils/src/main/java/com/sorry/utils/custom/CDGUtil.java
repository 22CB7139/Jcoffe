package com.sorry.utils.custom;


import com.esafenet.util.*;
import java.lang.reflect.Array;

public class CDGUtil {

    private static byte[] key2 = new byte[]{-21, -112, 90, -68, 5, 44, 85, -86, -21, -112, 90, -68, 5, 44, 85, -86};

    public CDGUtil() {
    }

    public static String encode(String str) throws Exception {
        CodeDecoder cd = new CodeDecoder();
        byte[] abyte1 = str.getBytes();
        int nLength = Array.getLength(abyte1);
        CodeDecoder.Encode(abyte1, nLength, key2);
        String src = new String(abyte1, "ISO8859_1");
        return CodeDecoder.getTransferEncrptString(src);
    }

    public static byte[] encode(byte[] abyte) throws Exception {
        CodeDecoder cd = new CodeDecoder();
        int nLength = Array.getLength(abyte);
        CodeDecoder.Encode(abyte, nLength, key2);
        return abyte;
    }

    public static void main(String[] args) {
        try {
            System.out.println(decode("DEEBEFDCCFCECDCG"));
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public static String decode(String info) throws Exception {
        CodeDecoder cd = new CodeDecoder();
        info = CodeDecoder.getTransferUnEncrptString(info);
        byte[] abyte2 = info.getBytes("ISO8859_1");
        int nLength = Array.getLength(abyte2);
        CodeDecoder.Decode(abyte2, nLength, key2);
        info = new String(abyte2);
        return info;
    }

    public static byte[] decode(byte[] abyte) throws Exception {
        CodeDecoder cd = new CodeDecoder();
        int nLength = Array.getLength(abyte);
        CodeDecoder.Decode(abyte, nLength, key2);
        return abyte;
    }

}

