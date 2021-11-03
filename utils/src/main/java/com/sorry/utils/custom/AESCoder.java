package com.sorry.utils.custom;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String SKEY = "WEAVERECOLOGYSECURITY3.0VERSION201607150857";

    public AESCoder() {
    }

    public static byte[] initSecretKey(String code) {
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(code.getBytes());
            kg.init(128, secureRandom);
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
            return new byte[0];
        }

        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    private static Key toKey(byte[] key) {
        return new SecretKeySpec(key, "AES");
    }

    public static InputStream encrypt(InputStream in, String code) throws Exception {
        byte[] key = initSecretKey(code);
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, k);
        CipherInputStream cis = new CipherInputStream(in, cipher);
        return cis;
    }

    public static OutputStream encrypt(OutputStream out, String code) throws Exception {
        byte[] key = initSecretKey(code);
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, k);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        return cos;
    }

    public static InputStream decrypt(InputStream in, String code) throws Exception {
        byte[] key = initSecretKey(code);
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, k);
        CipherInputStream cis = new CipherInputStream(in, cipher);
        return cis;
    }

    public static OutputStream decrypt(OutputStream out, String code) throws Exception {
        byte[] key = initSecretKey(code);
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, k);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        return cos;
    }

    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            if (sKey == null) {
                sKey = "WEAVERECOLOGYSECURITY3.0VERSION201607150857";
            }

            byte[] raw = initSecretKey(sKey);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, skeySpec);
            byte[] encrypted1 = hex2byte(sSrc);

            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception var8) {
                var8.printStackTrace();
                return null;
            }
        } catch (Exception var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            sKey = "WEAVERECOLOGYSECURITY3.0VERSION201607150857";
        }

        byte[] raw = initSecretKey(sKey);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return byte2hex(encrypted).toLowerCase();
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        } else {
            int l = strhex.length();
            if (l % 2 == 1) {
                return null;
            } else {
                byte[] b = new byte[l / 2];

                for(int i = 0; i != l / 2; ++i) {
                    b[i] = (byte)Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
                }

                return b;
            }
        }
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    public static String randomKey() {
        String keys = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int length = (int)(Math.random() * 16.0D + 16.0D);
        int i = 0;
        String key = "";

        while(i < length) {
            int j = (int)(Math.random() * 61.0D);
            if (j <= 61) {
                key = key + keys.substring(j, j + 1);
                ++i;
            }
        }

        return key;
    }

    public static void main(String[] args) throws Exception {
        //String encrypt = encrypt((String)"ecologyxindaoa@weaver.com.cn", (String)null);
        String encrypt = "ae6502f1926b3f2dfcc591eba32fca6d6af38c5ea5a167bf571773031b86ea01ea2e0fb69cf81d4405c5635f9d6715e32bdd74c5f0217464707ed01bf4d0ae84add2669e75e48cd4f64902f7b49f9fdaddbd6aaeecf2b9a332e48339e1aceeb83f6622f4f4771c3830d87b8188690a262d7ef0e75dc0f54ee65f63ea77a3c052ad30ae81978490c6b879bd0a347777946226480583c6ac65288fe34562cccf1b167fa398664a738c87622a4d36f0d3d34d1d7d47f905f6ce9838c1b8f7ba8eed887f6a18fc4e23c3b294eedcb24042bcfd037884fe3fc82247d535e913bb954aeb8aaf5b77072a5c96d8650f8ac8c83342dd381c73004c4782baa145c7fde77462dc69ba9652b8f37ebf475063e9f56516cf79f3eb9a334e4faa788fbc92ad23c363bd1b599593fbc2a962f460808a11f8d0877636bd3865610b54577ce49d886f50683a595d617854cede7b2edf7e97044ce43c4b817e3679598e62f744d8e221fe6c09e26ff89f815b28a6fafec3ad6aa39a2751c91b8152731ab1d9ff0c19230a6c6f028b7b98fcda6c491c5689f3cce017b2ba358f7c35ab1f14facd743e2c45bdf2f9037b097160d8b4f92ecb70c693010db536a96a7dd681718d65d287d645d95997e4978eb78f3ad9365ad736aedee47b09a062d89197263a096b5b2574995fe8d6f46612ef1ccaca8df267dc6508eedfe7cd597a076ee4ee46ca1489b0075a6b0d0a7a81eefe5bdf3240bc747ed9158be23c078f798ed4206f3e0fa924db98ba65a5ae628302bc92bf9861d161b87791122eb253a0f69f4d92532963";
        System.out.println(encrypt);
        System.out.println(decrypt((String)encrypt, (String)null));
    }
}
