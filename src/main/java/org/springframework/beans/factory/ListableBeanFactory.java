package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * @author derekyi
 * @date 2020/11/28
 */
public interface ListableBeanFactory extends BeanFactory {

	/**
	 * Return all instances of the specified type
	 *
	 * @param type
	 * @param <T>
	 * @return
	 * @throws BeansException
	 */
	<T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

	/**
	 * Returns the names of all defined beans
	 *
	 * @return
	 */
	String[] getBeanDefinitionNames();
}
