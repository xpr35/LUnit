package com.xpr35.lunit;

import com.xpr35.lunit.annotation.After;
import com.xpr35.lunit.annotation.Before;
import com.xpr35.lunit.annotation.Ignore;
import com.xpr35.lunit.annotation.Test;

import java.lang.reflect.Method;
import java.util.Queue;

/**
 * Created by xpres on 24/03/18.
 */
public class Worker implements Runnable {
    private final Queue<TestInst> testMethodQueue;
    private final Queue<String> reportQueue;

    public Worker(Queue<TestInst> testMethodQueue, Queue<String> reportQueue) {
        this.testMethodQueue = testMethodQueue;
        this.reportQueue = reportQueue;
    }

    /**
     * Method runs test class methods with {@link Test} annotations
     */
    public void run() {
        while (!testMethodQueue.isEmpty()) {
            TestInst testInst;
            synchronized (testMethodQueue) {
                testInst = testMethodQueue.poll();
            }
            String result = "Cannot find class";
            if (!testInst.getTest().isAnnotationPresent(Ignore.class)) {
                try {
                    runBefore(testInst);
                    testInst.getTest().invoke(testInst.getClazz().newInstance());
                    result = testInst.getTest().getName() + " OK";
                    runAfter(testInst);
                } catch (Throwable e) {
                    if (testInst.getTest().getAnnotation(Test.class).expected() == e.getCause().getClass()) {
                        result = testInst.getTest().getName() + " OK";
                        runAfter(testInst);
                    } else {
                        result = testInst.getTest().getName() + " Failed: " + e.getCause();
                    }
                }
            } else {
                result = testInst.getTest().getName() + " Ignored";
            }
            reportQueue.add(result);
        }
    }

    /**
     * Method runs test class methods with {@link Before} annotations
     */
    private void runBefore(TestInst testInst) {
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
    private void runAfter(TestInst testInst) {
        for (Method afterMethod : testInst.getAfter()) {
            try {
                afterMethod.invoke(testInst.getClazz().newInstance());
            } catch (Throwable e) {
                System.err.println("Cannot invoke after");
            }
        }
    }
}
