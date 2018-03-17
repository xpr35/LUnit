package com.xpr35.lunit;

import com.xpr35.lunit.annotation.After;
import com.xpr35.lunit.annotation.Before;
import com.xpr35.lunit.annotation.Ignore;
import com.xpr35.lunit.annotation.Test;

import java.lang.reflect.Method;

/**
 * Created by xpres on 16/03/18.
 */

public class Main {
    public static void main(String[] args) {
        Class<AnyRandomClassTest> clazz = AnyRandomClassTest.class;
        for (Method beforeMethod : clazz.getDeclaredMethods()) {
            if (beforeMethod.isAnnotationPresent(Before.class)) {
                try {
                    beforeMethod.invoke(clazz.newInstance());
                } catch (Throwable e) {
                    System.err.println("Cannot invoke before");
                }
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                if (!method.isAnnotationPresent(Ignore.class)) {
                    try {
                        method.invoke(clazz.newInstance());
                        System.out.println(method.getName() + " OK");
                    } catch (Throwable ex) {
                        System.out.println(method.getName() + " Failed: " + ex.getCause());
                    }
                } else {
                    System.out.println(method.getName() + " Ignored");
                }
            }

        }
        for (Method afterMethod : clazz.getDeclaredMethods()) {
            if (afterMethod.isAnnotationPresent(After.class)) {
                try {
                    afterMethod.invoke(clazz.newInstance());
                } catch (Throwable e) {
                    System.err.println("Cannot invoke after");
                }
            }
        }
    }
}
