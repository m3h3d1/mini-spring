package org.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author derekyi
 * @date 2020/12/26
 */
public class ClassPathScanningCandidateComponentProvider {

	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
		// Scan classes annotated with org.springframework.stereotype.Component
		Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
		for (Class<?> clazz : classes) {
			BeanDefinition beanDefinition = new BeanDefinition(clazz);
			candidates.add(beanDefinition);
		}
		return candidates;
	}
}
