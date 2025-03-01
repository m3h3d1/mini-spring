package org.springframework.core.convert;

/**
 * Type conversion abstract interface
 *
 * @author derekyi
 * @date 2021/1/10
 */
public interface ConversionService {

	boolean canConvert(Class<?> sourceType, Class<?> targetType);

	<T> T convert(Object source, Class<T> targetType);
}
