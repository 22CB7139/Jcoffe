package com.sorry.utils.bytetricks;

import com.sorry.utils.bytesandfiles.TransforBytes;
import javassist.ClassPool;
import javassist.CtClass;
/**
 * created by 0x22cb7139 on 2021/7/12
 */
public class javassist {

    public static void main(String[] args) {
        try{

            //获取jvm加载的类库
            ClassPool pool = ClassPool.getDefault();
            //获取已知类
            CtClass clazz = pool.get("com.sorry.utils.templatesimpl.CustomAbstractTranslet");
            clazz.makeClassInitializer().insertAfter("System.out.println(\"insert static\");");
            clazz.setName("com.baidu.openrasp.InvokeHandler" + System.currentTimeMillis());
            final byte[] classBytes = clazz.toBytecode();
            TransforBytes.CreateFileFromBytes(classBytes,clazz.getName().substring(clazz.getName().lastIndexOf(".")+1)+".class");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
