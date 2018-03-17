package com.xpr35.lunit;

import com.xpr35.lunit.annotation.After;
import com.xpr35.lunit.annotation.Before;
import com.xpr35.lunit.annotation.Ignore;
import com.xpr35.lunit.annotation.Test;

import static com.xpr35.lunit.assertions.Assertions.assertEquals;

/**
 * Created by xpres on 16/03/18.
 */
public class AnyRandomClassTest {

    @Before
    public void beforeMethodOne() {
        System.out.println("Success first before method invocation");
    }

    @Before
    public void beforeMethodTwo() {
        System.out.println("Success second before method invocation");
    }

    @Test
    public void firstTest() {
        assertEquals("one", "one");
    }

    @Test
    @Ignore
    public void secondTest() {
        assertEquals("two", "none");
    }

    @Test
    public void thirdTest() {
        assertEquals("one", "three");
    }

    @After
    public void afterMethodOne() {
        System.out.println("Success first after method invocation");
    }

    @After
    public void afterMethodTwo() {
        System.out.println("Success second after method invocation");
    }
}
