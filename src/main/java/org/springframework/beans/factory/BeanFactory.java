package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * bean container
 *
 * @author derekyi
 * @date 2020/11/22
 */
public interface BeanFactory {

	/**
	 * Get bean
	 *
	 * @param name
	 * @return
	 * @throws BeansException when the bean does not exist
	 */
	Object getBean(String name) throws BeansException;

	/**
	 * Find bean by name and type
	 *
	 * @param name
	 * @param requiredType
	 * @param <T>
	 * @return
	 * @throws BeansException
	 */
	<T> T getBean(String name, Class<T> requiredType) throws BeansException;

	<T> T getBean(Class<T> requiredType) throws BeansException;

	boolean containsBean(String name);
}
