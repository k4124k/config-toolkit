package com.dangdang.config.service.file.protocol;

import com.dangdang.config.service.exception.InvalidPathException;
import com.dangdang.config.service.file.FileChangeEventListener;
import com.dangdang.config.service.file.FileConfigGroup;
import com.dangdang.config.service.file.FileLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.net.URI;


/**
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 */
public abstract class LocalFileProtocol implements Protocol {

	/*
    //jdk7
	private WatchService watcher;

	@Override
	public final byte[] read(FileLocation location) throws InvalidPathException {
		try {
			Path path = getPath(location);
			if (!Files.isReadable(path)) {
				throw new InvalidPathException("The file is not readable.");
			}
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new InvalidPathException(e);
		}
	}

	@Override
	public final void watch(FileLocation location, FileConfigGroup fileConfigGroup) throws InvalidPathException {
		// Register file change listener
		try {
			watcher = FileSystems.getDefault().newWatchService();
			Path path = getPath(location);

			path.getParent().register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
			new Thread(new FileChangeEventListener(watcher, fileConfigGroup, path)).start();
		} catch (IOException e) {
			throw new InvalidPathException(e);
		}
	}

	protected abstract Path getPath(FileLocation location) throws InvalidPathException;

	@Override
	public void close() throws IOException {
		if (watcher != null) {
			watcher.close();
		}
	}*/

    private FileAlterationObserver observer;
    private FileAlterationMonitor monitor;

    @Override
    public final byte[] read(FileLocation location) throws InvalidPathException {
        try {
            File file = new File(getPath(location));
            if (!file.canRead()) {
                throw new InvalidPathException("The file is not readable.");
            }
            return IOUtils.toByteArray(file.toURI());
        } catch (IOException e) {
            throw new InvalidPathException(e);
        }
    }

    @Override
    public final void watch(FileLocation location, FileConfigGroup fileConfigGroup) throws InvalidPathException {
        // Register file change listener
        try {
            File file = new File(getPath(location));
            // 创建一个文件观察器用于处理文件的格式
            observer = new FileAlterationObserver(file.getParentFile(), FileFilterUtils.and(
                    FileFilterUtils.fileFileFilter()));
            //设置文件变化监听器
            observer.addListener(new FileChangeEventListener(fileConfigGroup));
            monitor = new FileAlterationMonitor(100, observer);
            monitor.start();
//            new Thread(monitor).start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidPathException(e);
        }
    }

    protected abstract URI getPath(FileLocation location) throws InvalidPathException;

    @Override
    public void close() throws IOException {
        if (monitor != null) {
            try {
                monitor.stop();
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }


}
