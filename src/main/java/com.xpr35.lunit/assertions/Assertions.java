package com.xpr35.lunit.assertions;

/**
 * Created by xpres on 17/03/18.
 */
public class Assertions {
    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(expected + " != " + actual);
        }
    }
}
