/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.monitor;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 *
 *
 * @author Gan
 * @date 2017年12月14日 下午5:04:27
 * @version 1.0
 *
 */
public class FileSystemMonitor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemMonitor.class);
    
    private ExecutorService pool = Executors.newCachedThreadPool();
    
    private class PathWatcher {
        private WatchService watchService;
        private Map<Path, MonitorPoint> manifest = new HashMap<>();
        public PathWatcher() throws IOException {
            watchService = FileSystems.getDefault().newWatchService();
        }
        public void register(Path path, MonitorPoint point) throws IOException {
            path.register(watchService, point.getInterestOps());
            manifest.put(path, point);
            
            pool.submit(new PathWatcherTask());
        }
        public boolean hasManifestItem(Path path) {
            return manifest.containsKey(path);
        }
        private class PathWatcherTask implements Runnable {
            @Override
            public void run() {
                try {
                    while (true) {
                        WatchKey key = watchService.take();
                        IWatchEventHandler handler = manifest.get(key.watchable()).getHandler();
                        if (handler.isRunInNewThread()) {
                            pool.submit(() -> handler.handle(key));
                        } else {
                            handler.handle(key);
                        }
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor es = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        es.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("out: " + System.currentTimeMillis());
                    es.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("in");
                            } catch (Exception e) {
                                System.err.println("KK");
                            }
                            
                        }
                    });
                    System.out.println("before shutdown : " + System.currentTimeMillis());
                  es.shutdown();
                  System.out.println("after shutdown : " + System.currentTimeMillis());
                } catch (Exception e) {
                    System.out.println("LL" + e);
                }
                
            }
        });
        try {
//            System.out.println("before shutdown : " + System.currentTimeMillis());
//            es.shutdown();
//            System.out.println("after shutdown : " + System.currentTimeMillis());
            System.out.println("before awaitTermination : " + System.currentTimeMillis());
            es.awaitTermination(10000, TimeUnit.SECONDS);
            System.out.println("after awaitTermination : " + System.currentTimeMillis());
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private Map<String, MonitorPoint> points = null;
    private List<PathWatcher> watchers = null;
    
    public String addMonitorPoint(String key, MonitorPoint point) {
        if (points.containsKey(key)) {
            LOGGER.error("Fail to register a MonitorPoint{key={},path={}}. Cause by : Key has already existed", key, point.getPath());
            return null;
        }
        
        try {
            Path path = Paths.get(point.getPath()).toRealPath();
            for (PathWatcher watcher : watchers) {
                try {
                    if (!watcher.hasManifestItem(path)) {
                        watcher.register(path, point);
                        points.put(key, point);
                        return key;
                    }
                } catch (ClosedWatchServiceException e) {
                    LOGGER.warn("MonitorPoint{key={},path={}} try to register to a closed WatchService", key, point.getPath());
                }
            }
            
            PathWatcher newWatcher = new PathWatcher();
            newWatcher.register(path, point);
            watchers.add(newWatcher);
            points.put(key, point);
            return key;
            
        } catch (IOException e) {
            LOGGER.error("Fail to register a MonitorPoint{key={},path={}}. Cause by : {}", key, point.getPath(), e);
            return null;
        }
    }
    
    public String addMonitorPoint(MonitorPoint point) {
        String key = UUID.randomUUID().toString();
        return this.addMonitorPoint(key, point);
    }
    
    public void removeMonitorPoint(String key) {
        MonitorPoint point = points.remove(key);
    }
    
    public void modifyMonitorPoint(String key, MonitorPoint newPoint) {
        getMonitorPoint(key);
    }
    
    public MonitorPoint getMonitorPoint(String key) {
        return points.get(key);
    }
    
    /********************************************************
     * 单例实现
     ********************************************************/
    
    private FileSystemMonitor() {}
    public static FileSystemMonitor getInstance() {
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder {
        static final FileSystemMonitor INSTANCE = new FileSystemMonitor();
    }
    
    
}
