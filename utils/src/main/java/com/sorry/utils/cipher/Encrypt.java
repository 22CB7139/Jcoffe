package com.sorry.utils.cipher;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sorry.utils.bytecommon.TransforByte;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * created by 0x22cb7139 on 2021/7/20
 */
public class Encrypt {
    public static String MD5(String input) {
        return MD5(input, Charset.defaultCharset());
    }

    public static String MD5(String input, String charset) {
        return MD5(input, Charset.forName(charset));
    }

    public static String MD5(String input, Charset charset) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException arg8) {
            arg8.printStackTrace();
        }
        md.update(input.getBytes(charset));
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] tmp = md.digest();
        char[] str = new char[32];
        int k = 0;
        for (int result = 0; result < 16; result++) {
            byte byte0 = tmp[result];
            str[k++] = hexDigits[byte0 >>> 4 & 0xF];
            str[k++] = hexDigits[byte0 & 0xF];
        }
        String arg9 = new String(str);
        return arg9;
    }

    public static String encryptSES(String input, String key) throws Exception {
        return encryptSES(input, key, Charset.forName("GB2312"));
    }

    public static String encryptSES(String input, String key, Charset charset) throws Exception {
        SES ses = new SES(key);
        byte[] byte_input = input.getBytes(charset);
        int len = ses.getEncryptResultLength(byte_input);
        byte[] output = new byte[len];
        ses.encrypt(byte_input, output);
        return (new BASE64Encoder()).encode(output);
    }

    public static String decryptSES(String input, String key) throws Exception {
        return decryptSES(input, key, Charset.forName("GB2312"));
    }

    public static String decryptSES(String input, String key, Charset charset) throws Exception {
        SES ses = new SES(key);
        byte[] byte_input = (new BASE64Decoder()).decodeBuffer(input);
        byte[] temp_output = new byte[input.length()];
        int output_len = ses.decrypt(byte_input, byte_input.length, temp_output);
        byte[] ouput = new byte[output_len];
        System.arraycopy(temp_output, 0, ouput, 0, output_len);
        return new String(ouput, charset);
    }

    public static String encrypt3DES(String input, String key) {
        return encrypt3DES(input, key, Charset.forName("GB2312"));
    }

    public static String encrypt3DES(String input, String key, Charset charset) {
        try {
            return TransforByte.byte2hex(DES.encrypt(input.getBytes(charset.name()), key.getBytes()));
        } catch (Exception arg3) {
            return "";
        }
    }

    public static String decrypt3DES(String input, String key) {
        return decrypt3DES(input, key, Charset.forName("GB2312"));
    }

    public static String decrypt3DES(String input, String key, Charset charset) {
        try {
            return new String(DES.decrypt(TransforByte.hex2byte(input.getBytes()), key.getBytes()), charset.name());
        } catch (Exception arg3) {
            return "";
        }
    }
}
