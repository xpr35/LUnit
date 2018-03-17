package com.xpr35.lunit.exception;

/**
 * Created by xpres on 17/03/18.
 */
public class TestAssertionError extends RuntimeException {
    public TestAssertionError() {
        super();
    }

    public TestAssertionError(String message) {
        super(message);
    }
}
