package org.springframework.aop.framework;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.springframework.aop.AdvisedSupport;

/**
 * CGLIB dynamic proxy
 *
 * @author zqc
 * @date 2022/12/17
 */
public class CglibAopProxy implements AopProxy {

	private final AdvisedSupport advised;

	public CglibAopProxy(AdvisedSupport advised) {
		this.advised = advised;
	}


	@Override
	public Object getProxy() {
		// Create dynamic proxy enhancement class
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
		enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
		enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
		return enhancer.create();
	}

	/**
	 * Note: MethodInterceptor here is from CGLIB, and the MethodInterceptor in advised is from the AOP Alliance.
     * This class is defined to adapt the two.
	 */
	private static class DynamicAdvisedInterceptor implements MethodInterceptor {

		private final AdvisedSupport advised;

		private DynamicAdvisedInterceptor(AdvisedSupport advised) {
			this.advised = advised;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			// Get target object
			Object target = advised.getTargetSource().getTarget();
			Class<?> targetClass = target.getClass();
			Object retVal = null;
			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
			CglibMethodInvocation methodInvocation = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy);
			if (chain == null || chain.isEmpty()) {
				// Call proxy method
				retVal = methodProxy.invoke(target, args);
			} else {
				retVal = methodInvocation.proceed();
			}
			return retVal;
		}
	}

	private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

		private final MethodProxy methodProxy;

		public CglibMethodInvocation(Object proxy, Object target, Method method,
				Object[] arguments, Class<?> targetClass,
				List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
			super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
			this.methodProxy = methodProxy;
		}

		@Override
		public Object proceed() throws Throwable {
			return super.proceed();
		}
	}
}
