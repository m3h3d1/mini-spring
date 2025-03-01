package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * Extension point for modifying beans after instantiation
 *
 * @author derekyi
 * @date 2020/11/28
 */
public interface BeanPostProcessor {

	/**
	 * Executes before the bean initialization method
	 *
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

	/**
	 * Executes after the bean initialization method
	 *
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
