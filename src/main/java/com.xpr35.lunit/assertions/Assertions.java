package com.xpr35.lunit.assertions;

import com.xpr35.lunit.exception.TestAssertionError;

/**
 * Created by xpres on 17/03/18.
 */
public class Assertions {
    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new TestAssertionError(expected + " != " + actual);
        }
    }

    public static void assertTrue(boolean cond) {
        if (!cond) {
            throw new TestAssertionError(cond + "Not true");
        }
    }
}
