package com.hydra.core.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckMap {
	String value() default ""; //默认为空，即为注解所在方法的方法名。
	String namespace() default ""; //默认为空，即为无命名空间。
	boolean filterFlag() default false; //默认为假，为真时按照方法说明过滤参数。
}
