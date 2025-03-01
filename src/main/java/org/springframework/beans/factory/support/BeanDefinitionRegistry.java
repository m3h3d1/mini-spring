package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * Interface for the BeanDefinition registry
 *
 * @author derekyi
 * @date 2020/11/22
 */
public interface BeanDefinitionRegistry {

	/**
	 * Register BeanDefinition in the registry
	 *
	 * @param beanName
	 * @param beanDefinition
	 */
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

	/**
	 * Retrieve BeanDefinition by its name
	 *
	 * @param beanName
	 * @return
	 * @throws BeansException If the BeanDefinition cannot be found
	 */
	BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	/**
	 * Check if a BeanDefinition with the given name exists
	 *
	 * @param beanName
	 * @return
	 */
	boolean containsBeanDefinition(String beanName);

	/**
	 * Get the names of all registered beans
	 *
	 * @return
	 */
	String[] getBeanDefinitionNames();
}
