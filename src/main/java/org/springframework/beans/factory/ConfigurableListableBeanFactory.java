package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;

/**
 * @author derekyi
 * @date 2020/11/28
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

	/**
	 * Find BeanDefinition by name
	 *
	 * @param beanName
	 * @return
	 * @throws BeansException if BeanDefinition is not found
	 */
	BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	/**
	 * Pre-instantiate all singleton instances
	 *
	 * @throws BeansException
	 */
	void preInstantiateSingletons() throws BeansException;

	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
