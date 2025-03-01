package org.springframework.beans.factory.config;

import org.springframework.beans.PropertyValues;

import java.util.Objects;

/**
 * BeanDefinition instance stores information about the bean, 
 * such as its class type, method constructor arguments, properties, and scope.
 * For simplification, this class includes only the class type and bean properties.
 *
 * @author derekyi
 * @date 2020/11/22
 */
public class BeanDefinition {

	public static String SCOPE_SINGLETON = "singleton";

	public static String SCOPE_PROTOTYPE = "prototype";


	 /**
	  * Class of the bean
	  */
	 private Class beanClass;

	 /**
	  * Properties of the class
	  */
	private PropertyValues propertyValues;
	 /**
	  * Name of the initialization method (if any)
	  */
	private String initMethodName;
	 /**
	  * Name of the destruction method (if any)
	  */
	private String destroyMethodName;
	 /**
	  * Scope of the bean. Default is singleton
	  */
	private String scope = SCOPE_SINGLETON;

	private boolean singleton = true;

	private boolean prototype = false;

	/*/*
		Indicates whether the bean should be lazily initialized
	 */
	private boolean lazyInit=false;

	public BeanDefinition(Class beanClass) {
		this(beanClass, null);
	}

	public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
		this.beanClass = beanClass;
		this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
	}

	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
	}

	public boolean isSingleton() {
		return this.singleton;
	}

	public boolean isPrototype() {
		return this.prototype;
	}

	public Class getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public String getInitMethodName() {
		return initMethodName;
	}

	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}

	public String getDestroyMethodName() {
		return destroyMethodName;
	}

	public void setDestroyMethodName(String destroyMethodName) {
		this.destroyMethodName = destroyMethodName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BeanDefinition that = (BeanDefinition) o;
		return beanClass.equals(that.beanClass);
	}

	@Override
	public int hashCode() {
		return Objects.hash(beanClass);
	}

	public void setLazyInit(boolean b){
		lazyInit=b;
	}

	public boolean isLazyInit(){
		return lazyInit;
	}
}
