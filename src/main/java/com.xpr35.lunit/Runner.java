package com.xpr35.lunit;

import com.xpr35.lunit.annotation.After;
import com.xpr35.lunit.annotation.Before;
import com.xpr35.lunit.annotation.Ignore;
import com.xpr35.lunit.annotation.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xpres on 16/03/18.
 */

public class Runner {
    public static void main(String[] args) {
        List<Class<AnyRandomClassTest>> classes = Arrays.asList(AnyRandomClassTest.class);
        for (Class<AnyRandomClassTest> clazz : classes) {
            Runner runner = new Runner();
            runner.runBefore(clazz);
            runner.runTests(clazz);
            runner.runAfter(clazz);
        }
    }


    private void runBefore(Class clazz) {
        for (Method beforeMethod : clazz.getDeclaredMethods()) {
            if (beforeMethod.isAnnotationPresent(Before.class)) {
                try {
                    beforeMethod.invoke(clazz.newInstance());
                } catch (Throwable e) {
                    System.err.println("Cannot invoke before");
                }
            }
        }
    }

    private void runTests(Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                if (!method.isAnnotationPresent(Ignore.class)) {
                    try {
                        method.invoke(clazz.newInstance());
                        System.out.println(method.getName() + " OK");
                    } catch (Throwable e) {
                        System.out.println(method.getName() + " Failed: " + e.getCause());
                    }
                } else {
                    System.out.println(method.getName() + " Ignored");
                }
            }
        }
    }

    private void runAfter(Class clazz) {
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
