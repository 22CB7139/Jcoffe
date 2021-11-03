package com.sorry.utils.bytetricks;

import com.sorry.utils.bytecommon.TransforBytes;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
/**
 * created by 0x22cb7139 on 2021/7/12
 */
public class Javassist {

    public static byte[] ChangeClassName(String before,String after) {
        try{
            //获取jvm加载的类库
            ClassPool pool = ClassPool.getDefault();
            //导入包,倒是不如直接编译好,ysoserial直接class字节数组进去好.
            //pool.importPackage("");
            //获取已知类
            CtClass clazz = pool.get(before);
            //clazz.makeClassInitializer().insertAfter(TransforBytes.readFile(""));
            clazz.setName(after);
            final byte[] classBytes = clazz.toBytecode();
            return classBytes;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
