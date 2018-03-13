/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 *
 * @author Gan
 * @date 2018年2月2日 下午3:53:50
 * @version 1.0
 *
 */
public class PathWatcherPool {
    
    private FileSystemMonitor monitor;
    
    private List<PathWatcher> watchers;
    
    private ExecutorService watcherThreadPool = Executors.newCachedThreadPool();
    
    public PathWatcherPool(FileSystemMonitor monitor) {
        this.monitor = monitor;
    }
    
    public void register(MonitorPoint point) throws MonitorPointRegisterException {
        try {
            List<String> paths = getRegisterPaths(point);
            for (PathWatcher watcher : watchers) {
                paths = watcher.register(paths, point);
                if (paths.isEmpty()) {
                    return;
                }
            }
            
            if (!paths.isEmpty()) {
                startNewPathWatcher(paths, point);
            }
        } catch (IOException e) {
            unregister(point);
            throw new MonitorPointRegisterException("Fail to Register " + point, e);
        }
    }
    private List<String> getRegisterPaths(MonitorPoint point) throws IOException {
        Path path = getAbsoluteNormalizePath(point);
        List<String> paths = new ArrayList<>();
        if (point.isFile()) {
            paths.add(path.getParent().toString());
        } else if (point.isDirectory()) {
            if (!point.isRecursion()) {
                paths.add(path.toString());
            } else {
                Files.walkFileTree(path, new SimpleFileVisitor(paths));
            }
        }
        return paths;
    }
    private Path getAbsoluteNormalizePath(MonitorPoint point) {
        return Paths.get(point.getPath()).toAbsolutePath().normalize();
    }
    private void startNewPathWatcher(List<String> paths, MonitorPoint master) throws IOException {
        PathWatcher watcher = new PathWatcher(monitor);
        watcher.register(paths, master);
        watchers.add(watcher);
        
        watcherThreadPool.execute(() -> watcher.watch());
    }
    
    public void unregister(MonitorPoint point) {
        for (PathWatcher watcher : watchers) {
            watcher.unregister(point);
        }
    }
    
    private class SimpleFileVisitor extends java.nio.file.SimpleFileVisitor<Path> {
        private List<String> paths;
        public SimpleFileVisitor(List<String> paths) {
            this.paths = paths;
        }
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            paths.add(dir.toString());
            return FileVisitResult.CONTINUE;
        }
    }
    
}
