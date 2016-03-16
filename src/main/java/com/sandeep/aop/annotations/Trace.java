package com.sandeep.aop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
/**
 * <p>
 * This annotation is used to trigger tracing
 * </p>
 * 
 * @author Sandeep Nayak
 *
 */
public @interface Trace {
}
