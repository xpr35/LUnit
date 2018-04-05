package com.xpr35.lunit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class for Test annotations
 *
 * Created by xpres on 16/03/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    static class None extends Throwable {
        private None() {
        }
    }
    Class<? extends Throwable> expected() default None.class;
}