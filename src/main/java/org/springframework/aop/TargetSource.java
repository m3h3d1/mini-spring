package org.springframework.aop;

/**
 * Target Object for Proxy
 *
 * @author derekyi
 * @date 2020/12/6
 */
public class TargetSource {

	private final Object target;

	public TargetSource(Object target) {
		this.target = target;
	}

	public Class<?>[] getTargetClass() {
		return this.target.getClass().getInterfaces();
	}

	public Object getTarget() {
		return this.target;
	}

}
