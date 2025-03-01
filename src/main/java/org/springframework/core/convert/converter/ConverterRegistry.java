package org.springframework.core.convert.converter;

/**
 * Type Converter Registration Interface
 *
 * @author derekyi
 * @date 2021/1/10
 */
public interface ConverterRegistry {

	void addConverter(Converter<?, ?> converter);

	void addConverterFactory(ConverterFactory<?, ?> converterFactory);

	void addConverter(GenericConverter converter);
}
