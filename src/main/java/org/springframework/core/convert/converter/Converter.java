package org.springframework.core.convert.converter;

/**
 * Type conversion abstract interface
 *
 * @author derekyi
 * @date 2021/1/10
 */
public interface Converter<S, T> {

	/**
	 * Type conversion
	 */
	T convert(S source);
}
