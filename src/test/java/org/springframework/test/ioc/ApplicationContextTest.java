package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.bean.Car;
import org.springframework.test.bean.Person;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author derekyi
 * @date 2020/11/28
 */
public class ApplicationContextTest {

	@Test
	public void testApplicationContext() throws Exception {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

		Person person = applicationContext.getBean("person", Person.class);
		System.out.println(person);
		// The 'name' property was modified to 'ivy' in CustomBeanFactoryPostProcessor
		assertThat(person.getName()).isEqualTo("ivy");

		Car car = applicationContext.getBean("car", Car.class);
		System.out.println(car);
		// The 'brand' property was modified to 'lamborghini' in CustomerBeanPostProcessor
		assertThat(car.getBrand()).isEqualTo("lamborghini");
	}
}
