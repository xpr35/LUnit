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
//        System.out.println("before#1");
    }

    @Before
    public void beforeMethodTwo() {
//        System.out.println("before#2");
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
//        System.out.println("after#1");
    }

    @After
    public void afterMethodTwo() {
//        System.out.println("after#2");
    }
}
