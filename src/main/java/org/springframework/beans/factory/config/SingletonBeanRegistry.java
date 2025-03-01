package org.springframework.beans.factory.config;

/**
 * Singleton bean registry
 *
 * @author derekyi
 * @date 2020/11/22
 */
public interface SingletonBeanRegistry {

	Object getSingleton(String beanName);

	void addSingleton(String beanName, Object singletonObject);
}
