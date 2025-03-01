package org.springframework.context;

import org.springframework.beans.BeansException;

/**
 * @author derekyi
 * @date 2020/11/28
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

	/**
	 * Refresh application context
	 *
	 * @throws BeansException
	 */
	void refresh() throws BeansException;

	/**
	 * Close application context
	 */
	void close();

	/**
	 * Register a hook with the JVM to execute container shutdown operations before JVM termination
	 */
	void registerShutdownHook();

}
