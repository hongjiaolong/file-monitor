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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gan.filemonitor.MonitorPoint.MonitorMode;

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
    private Map<String, WatchedObject> watchedObjects;
    private FileSystemMonitor monitor;

    public PathWatcher(FileSystemMonitor monitor) throws IOException {
        this.service = FileSystems.getDefault().newWatchService();
        this.watchedObjects = new HashMap<>();
        this.monitor = monitor;
    }
    
    public List<String> register(List<String> paths, MonitorPoint master) throws IOException {
        if (!watchedObjects.isEmpty() 
            && watchedObjects.get(0).getMaster().getMonitorMode() == MonitorMode.EXCLUSIVE) {
            return paths;
        }
        
        switch (master.getMonitorMode()) {
        case EXCLUSIVE:
            if (watchedObjects.isEmpty()) {
                registerAll(paths, master);
                return Collections.emptyList();
            }
            return paths;
        case INSEPARABLE:
            List<String> intersection = getIntersection(new ArrayList<>(watchedObjects.keySet()), paths);
            if (intersection.isEmpty()) {
                registerAll(paths, master);
                return Collections.emptyList();
            }
            return paths;
        default:
            List<String> toRegister = getReduce(paths, new ArrayList<>(watchedObjects.keySet()));
            List<String> notRegister = getIntersection(new ArrayList<>(watchedObjects.keySet()), paths);
            registerAll(toRegister, master);
            return notRegister;
        }
    }
    private void registerAll(List<String> paths, MonitorPoint master) throws IOException {
        for (String pathString : paths) {
            Path path = Paths.get(pathString);
            WatchKey key = path.register(service, master.getInterestOps());
            watchedObjects.put(pathString, new WatchedObject(key, master));
        }
    }
    private <T> List<T> getIntersection(List<T> list1, List<T> list2) {
        return list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
    }
    private <T> List<T> getReduce(List<T> list1, List<T> list2) {
        return list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
    }
    
    public void unregister(List<String> paths) {
        paths.forEach(this::unregisterPath);
    }
    private void unregisterPath(String path) {
        WatchedObject object = watchedObjects.get(path);
        if (object.getWatchKey().isValid()) {
            object.getWatchKey().cancel();
        }
        watchedObjects.remove(path);
    }
    
    public void watch() {
        while (true) {
            try {
                WatchKey key = service.take();
                
                String path = key.watchable().toString();
                MonitorPoint master = watchedObjects.get(path).getMaster();
                
                List<WatchEvent<?>> events = key.pollEvents();
                for (WatchEvent<?> event : events) {
                    switch (master.getHandlerChainMode()) {
                    case RUN_IN_CURRENT_THREAD:
                        master.getHandlerChains().forEach(chain -> chain.callHandlers(event.context().toString(), event.kind(), event.count(), null));
                        break;
                    case RUN_IN_SEPERATED_THREAD:
                        monitor.getHandlerThreadPool().execute(() -> master.getHandlerChains().forEach(chain -> chain.callHandlers(event.context().toString(), event.kind(), event.count(), null)));
                        break;
                    }
                }
                if (!key.reset()) {
                    unregisterPath(path);
                }
            } catch (InterruptedException e) {
                continue;
            }
        }
    }
    
    private class WatchedObject {
        private WatchKey watchKey;
        private MonitorPoint master;
        public WatchedObject(WatchKey watchKey, MonitorPoint master) {
            this.watchKey = watchKey;
            this.master = master;
        }
        public WatchKey getWatchKey() {
            return watchKey;
        }
        public MonitorPoint getMaster() {
            return master;
        }
    }
    
}
