package org.springframework.context;

/**
 * Event Publisher Interface
 *
 * @author derekyi
 * @date 2020/12/5
 */
public interface ApplicationEventPublisher {

	/**
	 * Publish event
	 *
	 * @param event
	 */
	void publishEvent(ApplicationEvent event);
}
