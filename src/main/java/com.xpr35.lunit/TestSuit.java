package com.xpr35.lunit;

import com.xpr35.lunit.annotation.After;
import com.xpr35.lunit.annotation.Before;
import com.xpr35.lunit.annotation.Ignore;
import com.xpr35.lunit.annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xpres on 04/04/18.
 */
public class TestSuit {
    private ExecutorService executorService;

    private Queue<TestInst> testMethodQueue = new ConcurrentLinkedQueue<TestInst>();
    private Queue<ReportEntry> report = new ConcurrentLinkedQueue<ReportEntry>();

    public TestSuit(int n) {
        this.executorService = Executors.newFixedThreadPool(n);
    }

    public void addClass(Class clazz) {
        List<Method> beforeList = new ArrayList<Method>();
        List<Method> afterList = new ArrayList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeList.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterList.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                if (!method.isAnnotationPresent(Ignore.class)) {
                    testMethodQueue.add(new TestInst(clazz, beforeList, afterList, method));
                } else {
                    System.err.println("Ignored: " + method.getName());
                }
            }

        }
    }

    public void run() {
        try {
            executorService.submit(new Worker(testMethodQueue, report));
        } catch (Exception e) {
            System.err.println(e);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.err.println("Was interrupted" + e.getMessage());
        }
    }

    public String getReport() {
        StringBuilder sb = new StringBuilder();
        for (ReportEntry reportEntry : this.report) {
            sb.append(reportEntry.getClassName());
            sb.append(" ");
            sb.append(reportEntry.getMethodName());
            sb.append(" ");
            sb.append(reportEntry.getStatus());
            sb.append(" ");
            sb.append(reportEntry.getCause());
            sb.append("\n");
        }
        return sb.toString();
    }
}
