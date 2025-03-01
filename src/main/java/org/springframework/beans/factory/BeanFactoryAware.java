package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * Implementing this interface allows a bean to be aware of its owning BeanFactory
 *
 * @author derekyi
 * @date 2020/12/1
 */
public interface BeanFactoryAware extends Aware {

	void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
