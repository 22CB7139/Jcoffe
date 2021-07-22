package com.sorry.utils.cipher;


import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;

/**
 * created by 0x22cb7139 on 2021/4/17
 */
public class AES {
    private static SecureRandom secureRandom;
    private static int initializationVectorSize = 128;
    private static final int GCM_TAG_LENGTH = 16;
    private static String ivsstring = "0123456789ABCDEF";
    private static boolean flag=false;


    public static void main(String[] args) {
        String mode = "CBC";
        String paddingmode = "PKCS5Padding";
        String cipherkey = "kPH+bIxk5D2deZiIxcaaaA==";
        String plaintext = "Y29vcmRpbmF0ZWlzbm9vYg==";//coordinateisnoob
        String ciphertext = AES.aesencrypt(mode,paddingmode,plaintext,cipherkey);
        byte[] temp = AES.Attackdecrypt(mode,paddingmode,ciphertext,cipherkey);

        //想要的解密结果
        byte[] attackplaintext = Base64.decode("Y29vcmRpbmF0ZWlzeXlkcw==");
        //padding
        byte[] newbytes = pad(attackplaintext);
        System.out.println("newbytes:"+bytesToHex(newbytes));
        //单块异或得attackivs
        byte[] attackivs = new byte[16];
        for(int i=0;i<16;i++){
            attackivs[i]= (byte) (temp[i]^newbytes[i]);
        }
        System.out.println("原始明文:"+Base64.decodeToString(plaintext));
        System.out.println("attackivs:"+bytesToHex(attackivs));
        String ciphertext1 = Base64.encodeToString(byteMerger(attackivs,byteCut(Base64.decode(ciphertext),new byte[16])));
        System.out.println("ciphertext1:"+bytesToHex(Base64.decode(ciphertext1)));
        //实际上这里是目标自动解密，我们传过去的只是iv+ciphertext，中间值不变。
        String decrypt = aesdecrypt(mode,paddingmode,ciphertext1,cipherkey);
        System.out.println("攻击得到的明文:"+Base64.decodeToString(decrypt));

    }


    /*
    * @Description: AES加密
    * @Param: [mode, paddingmode, plaintxt, cipherkey]
    * @return: java.lang.String
    */
    public static String aesencrypt(String mode,String paddingmode,String plaintxt,String cipherkey){
        //AES指定
        String result="null";
        if(cipherkey == null){
            return null;
        }
        try{
            byte[] raw = Base64.decode(cipherkey);
            if(raw.length!=16){return null;}
            byte[] encoded = Base64.decode(plaintxt);
            //模式初始化
            Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+paddingmode);
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            if(mode=="CBC"){
                //头16字节作为iv
                byte[] ivs = generateInitializationVector();
                //byte[] ivspad = ivspadstring.getBytes();
                IvParameterSpec iv = new IvParameterSpec(ivs);
                cipher.init(1, key, iv);
                byte[] ret = cipher.doFinal(encoded);
                //result =  Base64.encodeToString(ret);//原本
                result =  Base64.encodeToString(byteMerger(ivs,ret));//shiro带iv
            }else if(mode=="GCM"){
                //头16字节作为iv
                byte[] ivs = generateInitializationVector();
                //GCM 标签等属性指定
                GCMParameterSpec iv = new GCMParameterSpec(GCM_TAG_LENGTH * 8,ivs);
                cipher.init(1, key,iv);
                //doFinal,偏移量。emmm Tag也在里面.
                byte[] ret = cipher.doFinal(encoded);
                //result =  Base64.encodeToString(ret);//原本
                result = Base64.encodeToString(byteMerger(ivs,ret));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


    /*
    * @Description: AES解密
    * @Param: [mode, paddingmode, ciphertxt, cipherkey]
    * @return: java.lang.String
    */
    public static String aesdecrypt(String mode,String paddingmode,String ciphertxt,String cipherkey){
        String result="";
        flag=false;
        if(cipherkey == null){
            return null;
        }
        try{
            byte[] raw = Base64.decode(cipherkey);
            if(raw.length!=16){return null;}
            byte[] decoded = Base64.decode(ciphertxt);
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            //模式初始化
            Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+paddingmode);
            if(mode=="CBC"){
                //byte[] ivs = generateInitializationVector();
                byte[] ivs= new byte[16];
                System.arraycopy(decoded,0,ivs,0,16);
                IvParameterSpec iv = new IvParameterSpec(ivs);
                cipher.init(2, key, iv);
                byte[] ret = cipher.doFinal(decoded);
                flag=true;
                result = Base64.encodeToString(byteCut(ret,new byte[16]));
            }else if(mode=="GCM"){
                //byte[] ivs = generateInitializationVector();
                //GCM 标签等属性指定
                GCMParameterSpec iv = new GCMParameterSpec(GCM_TAG_LENGTH * 8,decoded,0,16);
                cipher.init(2, key,iv);
                //解密后半段
                byte[] ret = cipher.doFinal(decoded,16,decoded.length-16);
                result = Base64.encodeToString(ret);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /*
    * @Description: Padding Oracle Attack
    * @Param: [ciphertext, cipherkey]
    * @return: byte[]
    */
    public static byte[] Attackdecrypt(String mode,String paddingmode,String ciphertext,String cipherkey){
        byte[] decoded = Base64.decode(ciphertext);
        //原始iv
        byte[] ivs= new byte[16];
        System.arraycopy(decoded,0,ivs,0,16);
        //构造恶意iv
        byte[] attackivs = new byte[16];
        byte[] temp = new byte[16];
        byte[] realdecoded = byteCut(decoded,ivs);
        boolean singleblock=realdecoded.length/16>1?false:true;
        byte[] plaintext = new byte[16];
        byte[] realdecodedblock = new byte[16];

        //分块
        for(int n=0;n<realdecoded.length/16;n++){
            //取分块
            System.arraycopy(realdecoded,n*16,realdecodedblock,0,16);
            //全位置0
            for(int i = 0;i<16;i++){
                attackivs[i]=0;
            }

            for(int x = 1;x<=16;x++){

                for(int y = 17-x;y<16;y++){
                    attackivs[y]=(byte)(temp[y]^(x));//temp[15]^0x01
                }
                //0x00-0xff
                for(int z = 0;z<256;z++){
                    attackivs[16-x] = (byte) z;
                    //添加iv到新密文
                    byte[] attackdecoded = byteMerger(attackivs,realdecodedblock);
                    String attackciphertext = Base64.encodeToString(attackdecoded);
                    aesdecrypt(mode,paddingmode,attackciphertext,cipherkey);
                    if(flag){
                        temp[16-x]=(byte)(x^z);//temp[15]=attackivs[15]^(遍历的)=x^(遍历出的)
                    }
                }
            }
            //单块
            if(singleblock){
                for(int i = 0;i<16;i++){
                    plaintext[i]=(byte)(ivs[i]^temp[i]);
                }
            }
            //多块，替换ivs
            else{
                for(int i = 0;i<16;i++){
                    plaintext[i]=(byte)(ivs[i]^temp[i]);
                }

            }
            ivs = realdecodedblock;
        }
        return temp;
    }

    private static byte[] pad(byte[] bytes){
        int padlength = bytes.length>16?bytes.length%16:16-bytes.length;
        if(padlength==0){padlength=16;}
        byte[] newbytes = new byte[bytes.length+padlength];
        System.arraycopy(bytes,0,newbytes,0,bytes.length);
        //padding
        for(int i=0;i<padlength;i++){
            newbytes[bytes.length+i]=(byte)(padlength);
        }
        return newbytes;
    }
    /*
    * @Description: 字节转hex
    * @Param: [b]
    * @return: java.lang.String
    */
    private static String byteToHex(byte b){
        String hexString = Integer.toHexString(b & 0XFF);
        if(hexString.length()<2){
            hexString = new StringBuilder(String.valueOf(0)).append(hexString).toString();
        }
        return hexString.toUpperCase();
    }

    /*
    * @Description: 字节数组转hex
    * @Param: [bytes]
    * @return: java.lang.String
    */
    public static String bytesToHex(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        if (bytes != null && bytes.length > 0)
        {
            for (int i = 0; i < bytes.length; i++) {
                String hex = byteToHex(bytes[i]);
                sb.append(hex);
            }
        }
        return sb.toString();
    }

    /*
    * @Description: hex转字节数组
    * @Param: [hexString]
    * @return: byte[]
    */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /*
    * @Description: 字符转字节
    * @Param: [c]
    * @return: byte
    */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /*
    * @Description: 字节数组合并
    * @Param: [bt1, bt2]
    * @return: byte[]
    */
    private static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    /*
    * @Description: 字节分割
    * @Param: [bt1, bt2]
    * @return: byte[]
    */
    private static byte[] byteCut(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length-bt2.length];
        System.arraycopy(bt1,bt2.length,bt3,0,bt1.length-bt2.length);
        return bt3;
    }
    /*
    * @Description: 创建随机IV
    * @Param: []
    * @return: byte[]
    */
    private static byte[] generateInitializationVector() {
        int size = getInitializationVectorSize();
        int sizeInBytes = size / 8;
        byte[] ivBytes = new byte[sizeInBytes];
        SecureRandom random = ensureSecureRandom();
        random.nextBytes(ivBytes);
        return ivBytes;
    }

    /*
    * @Description: 确保有随机数
    * @Param: []
    * @return: java.security.SecureRandom
    */
    private static SecureRandom ensureSecureRandom() {
        SecureRandom random = getSecureRandom();
        if (random == null)
            random = getDefaultSecureRandom();
        return random;
    }

    /*
    * @Description: 
    * @Param: []
    * @return: java.security.SecureRandom
    */
    private static SecureRandom getSecureRandom() {
        return secureRandom;
    }

    /*
    * @Description: 
    * @Param: []
    * @return: java.security.SecureRandom
    */
    private static SecureRandom getDefaultSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            return new SecureRandom();
        }
    }

    /*
    * @Description:
    * @Param: []
    * @return: int
    */
    private static int getInitializationVectorSize() {
        return initializationVectorSize;
    }
}
