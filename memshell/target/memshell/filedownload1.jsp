<%@page language="java" contentType="application/x-msdownload" pageEncoding="UTF-8"%><%
    response.reset();
    response.setContentType("application/x-download;charset=UTF-8");
    String filePath = request.getParameter("filePath");
    String fileName = request.getParameter("fileName");

   /*filePath = new String(filePath.getBytes("ISO8859_1"),"utf-8");
   fileName = new String(fileName.getBytes("ISO8859_1"),"utf-8");
   fileName = java.net.URLEncoder.encode(fileName, "UTF-8"); */
    fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
    fileName = fileName.replaceAll("\\+", "%20");
    String realPath = "";
    if( !filePath.matches(".*\\./.*") && !filePath.matches("(.*)WEB-(.*)"))
    {
        //realPath = getServletContext().getRealPath(filePath).replace("/\%/g","%25").replace("/\../g","%25");
        //realPath = getServletContext().getRealPath("./");
        realPath = getServletContext().getRealPath(filePath);

    }
    realPath = getServletContext().getRealPath(filePath);
    //realPath = getServletContext().getRealPath("./");
    if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1)
    {
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName+"===="+filePath+"======"+realPath);
    } else {
        response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
    }
    try{
        java.io.OutputStream os = response.getOutputStream();
        java.io.FileInputStream fis = new java.io.FileInputStream( realPath  );
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
















































