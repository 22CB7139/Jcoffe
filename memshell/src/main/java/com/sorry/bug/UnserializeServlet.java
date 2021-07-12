package com.sorry.bug;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Writer;

/**
 * created by 0x22cb7139 on 2021/7/9
 */
public class UnserializeServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException{
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        try{
            InputStream is = req.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            ois.readObject();
        }catch (Exception e){
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
