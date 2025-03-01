package org.springframework.aop;


/**
 * @author derekyi
 * @date 2020/12/5
 */
public interface Pointcut {

	ClassFilter getClassFilter();

	MethodMatcher getMethodMatcher();
}
