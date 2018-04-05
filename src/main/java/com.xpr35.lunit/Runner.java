package com.xpr35.lunit;

/**
 * Tests execution class
 * <p>
 * Created by xpres on 16/03/18.
 */
public class Runner {
    private static int DEFAULT_THREADSNUMBER = 3;

    public static void main(String[] args) {
        int numberOfThreads = DEFAULT_THREADSNUMBER;
        try {
            numberOfThreads = Integer.valueOf(args[args.length - 1]);
        } catch (NumberFormatException e) {
            System.err.println("Cannot parse number of threads param. Gonna use " +
                    DEFAULT_THREADSNUMBER + " sthreads as default");
        }
        TestSuit testSuit = new TestSuit(numberOfThreads);
        for (int i = 0; i < args.length - 1; i++) {
            try {
                Class clazz = Class.forName(args[i]);
                testSuit.addClass(clazz);
            } catch (ClassNotFoundException e) {
                System.err.println("Cannot find class: " + e.getCause());
            }
        }
        testSuit.run();
        System.out.println("\nTESTS:\n" + testSuit.report());
    }
}
