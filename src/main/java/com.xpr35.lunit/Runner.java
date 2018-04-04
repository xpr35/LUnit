package com.xpr35.lunit;

/**
 * Tests execution class
 * <p>
 * Created by xpres on 16/03/18.
 */
public class Runner {

    public static void main(String[] args) {
        TestSuit testSuit = new TestSuit(3);
        for (String s : args) {
            try {
                Class clazz = Class.forName(s);
                testSuit.addClass(clazz);
            } catch (ClassNotFoundException e) {
                System.err.println(e.getCause());
            }
        }
        testSuit.run();
        System.out.println("\nTESTS:\n"+ testSuit.report());
    }
}
