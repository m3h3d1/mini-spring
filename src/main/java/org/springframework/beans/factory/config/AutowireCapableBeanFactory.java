package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author derekyi
 * @date 2020/11/28
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

	/**
	 * Execute the postProcessBeforeInitialization method of BeanPostProcessors
	 *
	 * @param existingBean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException;

	/**
	 * Execute the postProcessAfterInitialization method of BeanPostProcessors
	 *
	 * @param existingBean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException;
}
