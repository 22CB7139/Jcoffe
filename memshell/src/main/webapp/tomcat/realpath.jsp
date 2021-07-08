<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.OutputStream" %><%
    response.reset();
    response.setContentType("application/x-download;charset=UTF-8");
    String k = request.getParameter("p");
    String s = request.getSession().getServletContext().getRealPath(k);
    OutputStream os = response.getOutputStream();
    FileInputStream fis = new FileInputStream(s);
    int length = fis.available();
    for(int i=0;i<length;i++){
        os.write(fis.read());
    }
    fis.close();
    os.flush();
    os.close();
    out.clear();
    out = pageContext.pushBody();

%>