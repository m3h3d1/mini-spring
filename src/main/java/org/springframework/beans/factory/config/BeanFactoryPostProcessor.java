package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * Allows custom modification of BeanDefinition properties
 *
 * @author derekyi
 * @date 2020/11/28
 */
public interface BeanFactoryPostProcessor {

	/**
	 * Provides a mechanism to modify BeanDefinition properties after all BeanDefinitions are loaded,
     * but before bean instantiation.
	 *
	 * @param beanFactory
	 * @throws BeansException
	 */
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
