package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * Abstract Application Context
 *
 * @author derekyi
 * @date 2020/11/28
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

	public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

	public static final String CONVERSION_SERVICE_BEAN_NAME = "conversionService";

	private ApplicationEventMulticaster applicationEventMulticaster;

	@Override
	public void refresh() throws BeansException {
		// Create BeanFactory and load BeanDefinitions
		refreshBeanFactory();
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();

		// Add ApplicationContextAwareProcessor to allow beans implementing ApplicationContextAware to sense the context
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

		// Execute BeanFactoryPostProcessor before bean instantiation
		invokeBeanFactoryPostProcessors(beanFactory);

		// Register BeanPostProcessor before other beans are instantiated
		registerBeanPostProcessors(beanFactory);

		// Initialize event publisher
		initApplicationEventMulticaster();

		// Register event listeners
		registerListeners();

		// Register type converters and pre-instantiate singleton beans
		finishBeanFactoryInitialization(beanFactory);

		// Trigger container refresh completion event
		finishRefresh();
	}

	protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
		// Set type converter
		if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME)) {
			Object conversionService = beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME);
			if (conversionService instanceof ConversionService) {
				beanFactory.setConversionService((ConversionService) conversionService);
			}
		}

		// Pre-instantiate singleton bean
		beanFactory.preInstantiateSingletons();
	}

	/**
	 * Create BeanFactory and load BeanDefinitions
	 *
	 * @throws BeansException
	 */
	protected abstract void refreshBeanFactory() throws BeansException;

	/**
	 * Execute BeanFactoryPostProcessor before bean instantiation
	 *
	 * @param beanFactory
	 */
	protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
		for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	/**
	 * Register BeanPostProcessor
	 *
	 * @param beanFactory
	 */
	protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
		for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
			beanFactory.addBeanPostProcessor(beanPostProcessor);
		}
	}

	/**
	 * Initialize event publisher
	 */
	protected void initApplicationEventMulticaster() {
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
		beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
	}

	/**
	 * Register event listener
	 */
	protected void registerListeners() {
		Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
		for (ApplicationListener applicationListener : applicationListeners) {
			applicationEventMulticaster.addApplicationListener(applicationListener);
		}
	}

	/**
	 * Publish container refresh complete event
	 */
	protected void finishRefresh() {
		publishEvent(new ContextRefreshedEvent(this));
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		applicationEventMulticaster.multicastEvent(event);
	}

	@Override
	public boolean containsBean(String name) {
		return getBeanFactory().containsBean(name);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(name, requiredType);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return getBeanFactory().getBeansOfType(type);
	}

	public <T> T getBean(Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(requiredType);
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return getBeanFactory().getBean(name);
	}

	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	public abstract ConfigurableListableBeanFactory getBeanFactory();

	public void close() {
		doClose();
	}

	public void registerShutdownHook() {
		Thread shutdownHook = new Thread() {
			public void run() {
				doClose();
			}
		};
		Runtime.getRuntime().addShutdownHook(shutdownHook);

	}

	protected void doClose() {
		// Publish container close event
		publishEvent(new ContextClosedEvent(this));

		// Execute destroy methods of singleton beans
		destroyBeans();
	}

	protected void destroyBeans() {
		getBeanFactory().destroySingletons();
	}
}

