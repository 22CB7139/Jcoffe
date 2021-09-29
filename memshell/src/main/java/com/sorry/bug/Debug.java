package com.sorry.bug;

import com.sorry.utils.bytetricks.Javassist;

import java.util.Base64;


/**
 * created by 0x22cb7139 on 2021/7/14
 */
public class Debug {
    public static void main(String[] args) {
        try{
            byte[] evil = Javassist.ChangeClassName("com.sorry.bug.CustomFilter","com.baidu.openrasp.InvokeFilter");
            String evil64 =  Base64.getEncoder().encodeToString(evil);
            System.out.println(evil64);
            /*
            //设置搜索类型包含ServletRequest，RequstGroup，Request...等关键字的对象
            List<Keyword> keys = new ArrayList<>();
            keys.add(new Keyword.Builder().setField_type("StandardContext").build());
            //新建一个广度优先搜索Thread.currentThread()的搜索器
            SearchRequstByBFS searcher = new SearchRequstByBFS(Thread.currentThread(),keys);
            //打开调试模式
            searcher.setIs_debug(true);
            //挖掘深度为20
            searcher.setMax_search_depth(20);
            //设置报告保存位置
            searcher.setReport_save_path("/Users/0x22cb7139/Desktop");
            searcher.searchObject();
             */
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
