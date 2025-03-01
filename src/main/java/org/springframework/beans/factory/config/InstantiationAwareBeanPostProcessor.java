package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;

/**
 * @author derekyi
 * @date 2020/12/6
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

	/**
	 * Executes before bean instantiation
	 *
	 * @param beanClass
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

	/**
	 * Executes after bean instantiation but before setting properties
	 *
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

	/**
	 * Executes after bean instantiation but before setting properties
	 *
	 * @param pvs
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
			throws BeansException;

	/**
	 * Exposes the bean early
	 *
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
