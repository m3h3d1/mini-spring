package org.springframework.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;

/**
 * Implementing this interface allows a bean to be aware of its owning ApplicationContext
 *
 * @author derekyi
 * @date 2020/12/1
 */
public interface ApplicationContextAware extends Aware {

	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
