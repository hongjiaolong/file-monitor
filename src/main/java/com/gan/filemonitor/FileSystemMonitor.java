/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gan.filemonitor.handler.IWatchEventHandler;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import core.GenerateNameException;
import core.NameManager;

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
    
    private PathWatcherPool watcherPool = new PathWatcherPool();
    
    private NameManager<MonitorPoint> manager = new NameManager<MonitorPoint>() {
        @Override
        protected String generateName(MonitorPoint point) throws GenerateNameException {
            if (StringUtils.isBlank(point.getName())) {
                throw new GenerateNameException(point + " has not a name");
            }
            if (exists(point.getName())) {
                throw new GenerateNameException(point + " has been registered");
            }
            return point.getName();
        }
    };
    
    private ExecutorService watcherThreadPool = Executors.newCachedThreadPool();
    private ExecutorService handlerThreadPool;
    
    public ExecutorService getWatcherThreadPool() {
        return watcherThreadPool;
    }

    public void setWatcherThreadPool(ExecutorService watcherThreadPool) {
        this.watcherThreadPool = watcherThreadPool;
    }

    public ExecutorService getHandlerThreadPool() {
        return handlerThreadPool;
    }

    public void setHandlerThreadPool(ExecutorService handlerThreadPool) {
        this.handlerThreadPool = handlerThreadPool;
    }

    public void addMonitorPoint(MonitorPoint point) throws MonitorException {
//        // 未启动
//        manager.register(point);
        
        // 启动
        try {
            manager.register(point);
            watcherPool.register(point);
        } catch (Exception e) {
            throw new MonitorException("Fail to add " + point, e);
        }
    }
    
    public boolean addMonitorPoint(String name, MonitorPoint point) {
        return false;
    }
    
    public boolean removeMonitorPoint(MonitorPoint point) {
        return false;
    }
    
    public boolean removeMonitorPoint(String name) {
        return false;
    }
    
    public void updateMonitorPoint(MonitorPoint point) {
        
    }
    
    public void updateMonitorPoint(String name, MonitorPoint newPoint) {
        
    }
    
    public MonitorPoint getMonitorPoint(String name) {
        return null;
    }
    
    public List<MonitorPoint> getAllMonitorPoints() {
        return null;
    }
    
    public void init() {
        
    }
    
    public void start() {
        
    }
    
    public void stop() {
        
    }
    
    public void destroy() {
        
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
    
    public static void main(String[] args) throws Exception {
        FileSystemMonitor monitor = new FileSystemMonitor();
        monitor.addMonitorPoint(new MonitorPoint());
    }
    
}
