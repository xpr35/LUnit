package com.xpr35.lunit;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by xpres on 04/04/18.
 */
public class TestInst {
    private Class clazz;
    private List<Method> before;
    private List<Method> after;
    private Method test;

    public TestInst(Class clazz,
                    List<Method> before,
                    List<Method> after,
                    Method test) {
        this.clazz = clazz;
        this.before = before;
        this.after = after;
        this.test = test;
    }

    public List<Method> getBefore() {
        return before;
    }

    public void setBefore(List<Method> before) {
        this.before = before;
    }

    public List<Method> getAfter() {
        return after;
    }

    public void setAfter(List<Method> after) {
        this.after = after;
    }

    public Method getTest() {
        return test;
    }

    public void setTest(Method test) {
        this.test = test;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
