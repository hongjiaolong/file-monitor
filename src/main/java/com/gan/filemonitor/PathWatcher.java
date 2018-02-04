/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程不安全
 *
 * @author Gan
 * @date 2018年2月2日 下午3:52:53
 * @version 1.0
 *
 */
public class PathWatcher {
    
    private WatchService service;
    private Map<String, WatchKey> paths;
    private FileSystemMonitor monitor;

    public PathWatcher(FileSystemMonitor monitor) throws IOException {
        this.service = FileSystems.getDefault().newWatchService();
        this.paths = new HashMap<>();
        this.monitor = monitor;
    }

    public void register(MonitorPoint point) throws IOException {
        Path path = getAbsoluteNormalizePath(point);
        paths.put(path.toString(), path.register(service, point.getInterestOps()));
        
        monitor.getWatcherThreadPool().execute(() -> {
            while (true) {
                try {
                    WatchKey key = service.take();
                    List<WatchEvent<?>> events = key.pollEvents();
                    for (WatchEvent<?> event : events) {
                        point.getHandlerChains().forEach(chain -> {
                            if (point.isRunInSeperateThread(chain)) {
                                
                            }
                            chain.callHandlers(event.context().toString(), event.kind(), event.count(), null);
                            
                        });
                    }
                    if (!key.reset()) {
//                        paths
                    }
                } catch (InterruptedException e) {
                    
                }
            }
        });
    }

    public void cancel(MonitorPoint point) {
        paths.remove(getAbsoluteNormalizePath(point).toString());
    }

    public boolean isRegistered(MonitorPoint point) {
        return paths.containsKey(getAbsoluteNormalizePath(point).toString());
    }

    private Path getAbsoluteNormalizePath(MonitorPoint point) {
        return Paths.get(point.getPath()).toAbsolutePath().normalize();
    }
}
