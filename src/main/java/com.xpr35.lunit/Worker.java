package com.xpr35.lunit;

import com.xpr35.lunit.annotation.After;
import com.xpr35.lunit.annotation.Before;
import com.xpr35.lunit.annotation.Test;

import java.lang.reflect.Method;
import java.util.Queue;

/**
 * Created by xpres on 24/03/18.
 */
public class Worker implements Runnable {
    private final Queue<TestInst> testMethodQueue;
    private final Queue<ReportEntry> reportQueue;

    public Worker(Queue<TestInst> testMethodQueue, Queue<ReportEntry> reportQueue) {
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
            ReportEntry result = new ReportEntry(testInst.getClazz().getName(),
                    testInst.getTest().getName(),
                    "Error",
                    "Cannot find class");
            try {
                runBefore(testInst);
                testInst.getTest().invoke(testInst.getClazz().newInstance());
                if (testInst.getTest().getAnnotation(Test.class).expected() == Test.None.class) {
                    result = new ReportEntry(testInst.getClazz().getName(),
                            testInst.getTest().getName(),
                            "Success");
                } else {
                    result = new ReportEntry(testInst.getClazz().getName(),
                            testInst.getTest().getName(),
                            "Failed",
                            "Was expected exception: "
                                    + testInst.getTest().getAnnotation(Test.class).expected());
                }
                runAfter(testInst);
            } catch (Throwable e) {
                if (testInst.getTest().getAnnotation(Test.class).expected() == e.getCause().getClass()) {
                    result = new ReportEntry(testInst.getClazz().getName(),
                            testInst.getTest().getName(),
                            "Success");
                    runAfter(testInst);
                } else {
                    result = new ReportEntry(testInst.getClazz().getName(),
                            testInst.getTest().getName(),
                            "Failed",
                            e.getCause().getMessage());
                }
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
                System.err.println("Cannot invoke @Before methods");
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
                System.err.println("Cannot invoke @After methods");
            }
        }
    }
}
