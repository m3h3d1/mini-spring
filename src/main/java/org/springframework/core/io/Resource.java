package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for resource abstraction and access
 *
 * @author derekyi
 * @date 2020/11/25
 */
public interface Resource {

	InputStream getInputStream() throws IOException;

}
