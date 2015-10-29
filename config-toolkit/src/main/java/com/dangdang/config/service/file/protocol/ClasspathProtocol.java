package com.dangdang.config.service.file.protocol;

import com.dangdang.config.service.exception.InvalidPathException;
import com.dangdang.config.service.file.FileLocation;
import com.google.common.io.Resources;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class ClasspathProtocol extends LocalFileProtocol {

	@Override
	protected URI getPath(FileLocation location) throws InvalidPathException {
		try {
			return Resources.getResource(location.getFile()).toURI();
		} catch (URISyntaxException e) {
			throw new InvalidPathException(e);
		}
	}

}
