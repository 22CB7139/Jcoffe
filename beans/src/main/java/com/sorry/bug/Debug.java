package com.sorry.bug;

/**
 * created by 0x22cb7139 on 2021/7/1
 */
public class Debug {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class clazz = Class.forName("com.sorry.bug.Execute");
        clazz.newInstance();

    }
}
