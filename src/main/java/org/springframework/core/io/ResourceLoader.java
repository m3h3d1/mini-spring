package org.springframework.core.io;

/**
 * Resource Loader Interface
 *
 * @author derekyi
 * @date 2020/11/25
 */
public interface ResourceLoader {

	Resource getResource(String location);
}
