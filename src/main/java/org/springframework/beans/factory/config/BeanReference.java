package org.springframework.beans.factory.config;

/**
 * A reference from one bean to another
 *
 * @author derekyi
 * @date 2020/11/24
 */
public class BeanReference {

	private final String beanName;


	public BeanReference(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}
}
