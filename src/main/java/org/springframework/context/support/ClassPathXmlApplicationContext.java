package org.springframework.context.support;

import org.springframework.beans.BeansException;

/**
 * Application context of the XML file
 *
 * @author derekyi
 * @date 2020/11/28
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	private String[] configLocations;

	/**
	 * Load BeanDefinition from the xml file and automatically refresh the context
	 *
	 * @param configLocation xml configuration file
	 * @throws BeansException Failed to create application context
	 */
	public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
		this(new String[]{configLocation});
	}

	/**
	 * Load BeanDefinition from xml file and automatically refresh the context
	 *
	 * @param configLocations xml configuration file
	 * @throws BeansException application context creation failed
	 */
	public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
		this.configLocations = configLocations;
		refresh();
	}

	protected String[] getConfigLocations() {
		return this.configLocations;
	}
}
