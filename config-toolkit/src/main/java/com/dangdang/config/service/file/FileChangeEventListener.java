package com.dangdang.config.service.file;

import com.dangdang.config.service.file.protocol.LocalFileProtocol;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Watcher for file changes use FileAlterationListenerAdaptor
 * 
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class FileChangeEventListener extends FileAlterationListenerAdaptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileProtocol.class);

	private FileConfigGroup fileConfigGroup;

	public FileChangeEventListener( FileConfigGroup fileConfigGroup){
		this.fileConfigGroup = fileConfigGroup;
	}

	@Override
	public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
	}

	@Override
	public void onDirectoryCreate(File directory) {

	}

	@Override
	public void onDirectoryChange(File directory) {

	}

	@Override
	public void onDirectoryDelete(File directory) {

	}

	@Override
	public void onFileCreate(File file) {

	}

	@Override
	public void onFileChange(File file) {
        LOGGER.debug("File {} changed.", file.getName());
		fileConfigGroup.initConfigs();
	}

	@Override
	public void onFileDelete(File file) {

	}

	@Override
	public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }

}
