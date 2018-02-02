/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import java.io.IOException;
import java.nio.file.Path;
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
    
    private List<PathWatcher> watchers;
    
    private ExecutorService watcherThreadPool = Executors.newCachedThreadPool();
    
    public void register(MonitorPoint point) throws IOException {
        for (PathWatcher watcher : watchers) {
            if (!watcher.isRegistered(point)) {
                watcher.register(point);
                return;
            }
        }
        
        PathWatcher watcher = new PathWatcher();
        watcher.register(point);
        watchers.add(watcher);
    }

}
