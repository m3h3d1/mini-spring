package org.springframework.test.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author derekyi
 * @date 2020/11/28
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("CustomBeanFactoryPostProcessor#postProcessBeanFactory");
		BeanDefinition personBeanDefiniton = beanFactory.getBeanDefinition("person");
		PropertyValues propertyValues = personBeanDefiniton.getPropertyValues();
		// Change the `name` attribute of `person` to `ivy`.
		propertyValues.addPropertyValue(new PropertyValue("name", "ivy"));
	}
}
