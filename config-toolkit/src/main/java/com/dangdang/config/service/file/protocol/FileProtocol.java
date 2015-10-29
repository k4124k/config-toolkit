package com.dangdang.config.service.file.protocol;

import java.io.File;
import java.net.URI;

import com.dangdang.config.service.exception.InvalidPathException;
import com.dangdang.config.service.file.FileLocation;

/**
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class FileProtocol extends LocalFileProtocol {
/*
     //jdk7
	@Override
	protected Path getPath(FileLocation location) throws InvalidPathException {
		return Paths.get(location.getFile());
	}
*/

	@Override
	protected URI getPath(FileLocation location) throws InvalidPathException {
        String path = location.getFile();
        File file = new File(path);
		if (file.exists()){
            return file.toURI();
        }else {
            throw new InvalidPathException("invalid path:" + path);
        }
	}

}
