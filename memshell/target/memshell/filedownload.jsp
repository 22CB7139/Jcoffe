<%@page language="java" contentType="application/x-msdownload" pageEncoding="UTF-8"%>
<%
  response.reset();
  response.setContentType("application/x-download;charset=UTF-8");
  String filePath = request.getParameter("filePath");
  //String filePath = "temp/../index.jsp";
  //String fileName = request.getParameter("fileName");
  //String fileName = "1.txt";

  /*filePath = new String(filePath.getBytes("UTF-8"), "ISO8859-1");*/
  //fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
  fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
  String realPath = "";
  if(filePath.matches(".*temp/.*") && !filePath.matches(".*\\./.*"))
    realPath = request.getServletContext().getRealPath(filePath);
    System.out.println(realPath);

  if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1)
  {
    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);
  } else {
    response.addHeader("Content-Disposition","attachment;filename=\"" + fileName+"\"");
  }
  try{
    java.io.OutputStream os = response.getOutputStream();
    java.io.FileInputStream fis = new java.io.FileInputStream( realPath );
    byte[] b = new byte[2048];
    int i = 0;
    while ( (i = fis.read(b)) > 0 ){
      os.write(b, 0, i);
    }
    fis.close();
    os.flush();
    os.close();
    //incase: getOutputStream() has already been called for this response?
    response.flushBuffer();
    out.clear();
    out = pageContext.pushBody();
  }catch(Exception e){
    System.out.println("下载错误!");
    e.printStackTrace();
  }
%>
