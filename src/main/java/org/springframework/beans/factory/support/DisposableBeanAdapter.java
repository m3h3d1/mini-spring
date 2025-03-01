package org.springframework.beans.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author derekyi
 * @date 2020/11/29
 */
public class DisposableBeanAdapter implements DisposableBean {

	private final Object bean;

	private final String beanName;

	private final String destroyMethodName;

	public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
		this.bean = bean;
		this.beanName = beanName;
		this.destroyMethodName = beanDefinition.getDestroyMethodName();
	}

	@Override
	public void destroy() throws Exception {
		if (bean instanceof DisposableBean) {
			((DisposableBean) bean).destroy();
		}

		// Avoid executing the destroy method twice if the bean implements DisposableBean and a custom method has the same name as a DisposableBean method
		if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
			// Execute the custom destroy method
			Method destroyMethod = ClassUtil.getPublicMethod(bean.getClass(), destroyMethodName);
			if (destroyMethod == null) {
				throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
			}
			destroyMethod.invoke(bean);
		}
	}
}
