package org.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.*;
import org.springframework.core.convert.ConversionService;

import java.lang.reflect.Method;

/**
 * @author derekyi
 * @date 2020/11/22
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
		implements AutowireCapableBeanFactory {

	private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
		// If bean requires proxy, return proxy object
		Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
		if (bean != null) {
			return bean;
		}

		return doCreateBean(beanName, beanDefinition);
	}

	/**
	 * Execute methods of InstantiationAwareBeanPostProcessor and return proxy if needed
	 *
	 * @param beanName
	 * @param beanDefinition
	 * @return
	 */
	protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
		Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
		if (bean != null) {
			bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
		}
		return bean;
	}

	protected Object applyBeanPostProcessorsBeforeInstantiation(Class beanClass, String beanName) {
		for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
			if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
				Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

	protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
		Object bean;
		try {
			bean = createBeanInstance(beanDefinition);

			// Handle circular dependency for singleton beans by exposing the bean early in the cache
			if (beanDefinition.isSingleton()) {
				Object finalBean = bean;
				addSingletonFactory(beanName, new ObjectFactory<Object>() {
					@Override
					public Object getObject() throws BeansException {
						return getEarlyBeanReference(beanName, beanDefinition, finalBean);
					}
				});
			}

			// Executes after the bean is instantiated
			boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
			if (!continueWithPropertyPopulation) {
				return bean;
			}
			// Allows BeanPostProcessor to modify properties before they are set
			applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
			// Populate bean properties
			applyPropertyValues(beanName, bean, beanDefinition);
			// Executes the bean's initialization methods and BeanPostProcessor's pre and post-processing methods
			bean = initializeBean(beanName, bean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}

		// Registers beans with destroy methods (implementing DisposableBean or custom destroy method)
		registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

		Object exposedObject = bean;
		if (beanDefinition.isSingleton()) {
			// Retrieves the proxy object if it exists
			exposedObject = getSingleton(beanName);
			addSingleton(beanName, exposedObject);
		}
		return exposedObject;
	}

	protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
		Object exposedObject = bean;
		for (BeanPostProcessor bp : getBeanPostProcessors()) {
			if (bp instanceof InstantiationAwareBeanPostProcessor) {
				exposedObject = ((InstantiationAwareBeanPostProcessor) bp).getEarlyBeanReference(exposedObject, beanName);
				if (exposedObject == null) {
					return exposedObject;
				}
			}
		}

		return exposedObject;
	}

	/**
	 * Run BeanPostProcessors after bean instantiation.
 	 * If this method returns false, the property population step will be skipped
	 *
	 * @param beanName
	 * @param bean
	 * @return
	 */
	private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
		boolean continueWithPropertyPopulation = true;
		for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
			if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
				if (!((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessAfterInstantiation(bean, beanName)) {
					continueWithPropertyPopulation = false;
					break;
				}
			}
		}
		return continueWithPropertyPopulation;
	}

	/**
	 * Allow BeanPostProcessor(s) to modify property values before they are applied to the bean
	 *
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 */
	protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
		for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
			if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
				PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
				if (pvs != null) {
					for (PropertyValue propertyValue : pvs.getPropertyValues()) {
						beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
					}
				}
			}
		}
	}

	/**
	 * Registers a bean that has a destroy method, either by implementing DisposableBean 
 	 * or defining a custom destroy method.
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 */
	protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
		// Only singleton beans execute their destroy methods
		if (beanDefinition.isSingleton()) {
			if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
				registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
			}
		}
	}

	/**
	 * Creates a new instance of the bean
	 *
	 * @param beanDefinition
	 * @return
	 */
	protected Object createBeanInstance(BeanDefinition beanDefinition) {
		return getInstantiationStrategy().instantiate(beanDefinition);
	}

	/**
	 * Sets the properties for the bean
	 *
	 * @param bean
	 * @param beanDefinition
	 */
	protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
		try {
			for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
				String name = propertyValue.getName();
				Object value = propertyValue.getValue();
				if (value instanceof BeanReference) {
					// Bean a depends on bean b, instantiate bean b first.
					BeanReference beanReference = (BeanReference) value;
					value = getBean(beanReference.getBeanName());
				} else {
					// Type conversion
					Class<?> sourceType = value.getClass();
					Class<?> targetType = (Class<?>) TypeUtil.getFieldType(bean.getClass(), name);
					ConversionService conversionService = getConversionService();
					if (conversionService != null) {
						if (conversionService.canConvert(sourceType, targetType)) {
							value = conversionService.convert(value, targetType);
						}
					}
				}

				// Set properties via reflection
				BeanUtil.setFieldValue(bean, name, value);
			}
		} catch (Exception ex) {
			throw new BeansException("Error setting property values for bean: " + beanName, ex);
		}
	}

	protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
		if (bean instanceof BeanFactoryAware) {
			((BeanFactoryAware) bean).setBeanFactory(this);
		}

		// Execute the pre-initialization phase of BeanPostProcessors
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

		try {
			invokeInitMethods(beanName, wrappedBean, beanDefinition);
		} catch (Throwable ex) {
			throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", ex);
		}

		// Execute the post-initialization phase of BeanPostProcessors
		wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		return wrappedBean;
	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}

	@Override
	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException {

		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}

	/**
	 * Execute the initialization method of the bean
	 *
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 * @throws Throwable
	 */
	protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Throwable {
		if (bean instanceof InitializingBean) {
			((InitializingBean) bean).afterPropertiesSet();
		}
		String initMethodName = beanDefinition.getInitMethodName();
		if (StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean && "afterPropertiesSet".equals(initMethodName))) {
			Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
			if (initMethod == null) {
				throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
			}
			initMethod.invoke(bean);
		}
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}
}
