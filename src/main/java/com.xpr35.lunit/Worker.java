package com.xpr35.lunit;

import com.xpr35.lunit.annotation.After;
import com.xpr35.lunit.annotation.Before;
import com.xpr35.lunit.annotation.Ignore;
import com.xpr35.lunit.annotation.Test;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by xpres on 24/03/18.
 */
public class Worker implements Callable {
    private TestInst testInst;

    public Worker(TestInst testInst) {
        this.testInst = testInst;
    }

    /**
     * Method runs test class methods with {@link Test} annotations
     */
    public String call() {
        String result = "Cannot find class";
        if (!testInst.getTest().isAnnotationPresent(Ignore.class)) {
            try {
                runBefore();
                testInst.getTest().invoke(testInst.getClazz().newInstance());
                result = testInst.getTest().getName() + " OK";
                runAfter();
            } catch (Throwable e) {
                result = testInst.getTest().getName() + " Failed: " + e.getCause();
            }
        } else {
            result = testInst.getTest().getName() + " Ignored";
        }
        return result;
    }

    /**
     * Method runs test class methods with {@link Before} annotations
     */
    private void runBefore() {
        for (Method beforeMethod : testInst.getBefore()) {
            try {
                beforeMethod.invoke(testInst.getClazz().newInstance());
            } catch (Throwable e) {
                System.err.println("Cannot invoke before");
            }
        }
    }

    /**
     * Method runs test class methods with {@link After} annotations
     */
    private void runAfter() {
        for (Method afterMethod : testInst.getAfter()) {
            try {
                afterMethod.invoke(testInst.getClazz().newInstance());
            } catch (Throwable e) {
                System.err.println("Cannot invoke after");
            }
        }
    }
}
