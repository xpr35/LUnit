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
import java.util.concurrent.Future;

/**
 * Created by xpres on 04/04/18.
 */
public class TestSuit {
    private ExecutorService executorService;

    private Queue<TestInst> testMethodQueue = new ConcurrentLinkedQueue<TestInst>();
    private Queue<String> report = new ConcurrentLinkedQueue<String>();

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
        while (!testMethodQueue.isEmpty()) {
            System.out.println(testMethodQueue.peek().getTest().getName());
            try {
                Future<String> fut = executorService.submit(new Worker(testMethodQueue.poll()));
                report.add(fut.get());
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        executorService.shutdown();
    }

    public String report() {
        return this.report.toString();
    }
}
