package org.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author derekyi
 * @date 2020/11/25
 */
public class DefaultResourceLoader implements ResourceLoader {

	public static final String CLASSPATH_URL_PREFIX = "classpath:";

	@Override
	public Resource getResource(String location) {
		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			// Resources under the Classpath
			return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
		} else {
			try {
				// Attempt to process as a URL
				URL url = new URL(location);
				return new UrlResource(url);
			} catch (MalformedURLException ex) {
				// Treat as a file system resource
				return new FileSystemResource(location);
			}
		}
	}
}
