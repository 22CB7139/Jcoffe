package com.sorry.utils.bytecommon;



import java.io.*;
import java.util.Base64;

/**
 * created by 0x22cb7139 on 2021/4/15
 */


public class TransforBytes {
    public static void main(String[] args) throws Exception {
        //BASE64Encoder encoder = new BASE64Encoder();
        //BASE64Decoder decoder = new BASE64Decoder();
        //byte[] bytes = decoder.decodeBuffer(args[1]);
        //CreateFileFromBytes(bytes,args[0]);
        //System.out.println(Arrays.toString(BytesFromFile("/Users/0x22cb7139/programing/java/spring/JCoffe/target/classes/com/sorry/jcoffe/Debug/Reflection/Person.class")));
        //String classb64 = new BASE64Encoder().encode(BytesFromFile("/Users/0x22cb7139/Desktop/p1k2_jsp$X.class"));
        String classb64 = Base64.getEncoder().encodeToString(BytesFromFile("/Users/0x22cb7139/programing/java/jcoffe/utils/target/classes/com/sorry/utils/templatesimpl/CustomAbstractTranslet.class"));
        System.out.println(classb64);
    }


    /*
    * @Description: 字节码转文件
    * @Param: [expbyte, targetname]
    * @return: void
    */
    public static void CreateFileFromBytes(byte[] expbyte,String targetname) throws IOException {
        FileOutputStream fos=new FileOutputStream(targetname);
        fos.write(expbyte);
        fos.close();
    }

    /*
    * @Description: 文件转字节码
    * @Param: [path]
    * @return: byte[]
    */
    public static byte[] BytesFromFile(String path) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        FileInputStream fis = new FileInputStream(path);
        byte[] b= new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        byte[] data = bos.toByteArray();
        bos.close();
        return data;
    }

    public static String readStringFromInputStream(InputStream inputStream) throws Exception{
        StringBuilder stringBuilder = new StringBuilder("");
        byte[] bytes = new byte[1024];
        int n = 0;
        while ((n=inputStream.read(bytes)) != -1){
            stringBuilder.append(new String(bytes,0,n));
        }
        return stringBuilder.toString();
    }

    public static String readFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\r\n");
                }
                return sb.toString();
            } else {
                System.err.println(String.format("[-] %s is not exists!", path));
                System.exit(0);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Object deserialize(byte[] serialized) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(serialized);
        ObjectInputStream objIn = new ObjectInputStream(in);
        return objIn.readObject();
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(obj);
        return out.toByteArray();
    }

}


