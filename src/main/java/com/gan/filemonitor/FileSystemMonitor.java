/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.name.GenerateNameException;
import core.name.NameManager;

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
    
    private PathWatcherPool watcherPool;
    
    private NameManager<MonitorPoint> manager;
    
    private ExecutorService watcherThreadPool = Executors.newCachedThreadPool();
    private ExecutorService handlerThreadPool;
    
    private MonitorState monitorState = MonitorState.NEW;
    
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
        if (monitorState == MonitorState.NEW || monitorState == MonitorState.DESTROYED) {
            throw new MonitorException("This operation is not supported in state " + monitorState);
        }
        
        try {
            if (manager.register(point) && monitorState == MonitorState.RUNNING) {
                watcherPool.register(point);
            }
        } catch (GenerateNameException e) {
            throw new MonitorException("Fail to add " + point, e);
        } catch (MonitorPointRegisterException e) {
            manager.cancel(point.getName());
            throw new MonitorException("Fail to add " + point, e);
        }
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
        watcherPool = new PathWatcherPool();
        manager = new NameManager<MonitorPoint>() {
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
        monitorState = MonitorState.INITED;
    }
    
    public void start() {
        for (MonitorPoint point : manager.getAllNameObjects()) {
            try {
                watcherPool.register(point);
            } catch (MonitorPointRegisterException e) {
                manager.cancel(point.getName());
                LOGGER.warn("Fail to register {}. Cause by : ", point, e);
            }
        }
        monitorState = MonitorState.RUNNING;
    }
    
    public void stop() {
        for (MonitorPoint point : manager.getAllNameObjects()) {
            try {
                watcherPool.register(point);
            } catch (MonitorPointRegisterException e) {
                manager.cancel(point.getName());
                LOGGER.warn("Fail to register {}. Cause by : ", point, e);
            }
        }
        monitorState = MonitorState.RUNNING;
        monitorState = MonitorState.STOPPED;
    }
    
    public void destroy() {
        monitorState = MonitorState.DESTROYED;
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
    
    /**
     * 监视器状态
     * 
     * NEW : 无资源，无监控，从未初始化过
     * INITED : 有资源，无监控
     * RUNNING : 有资源，有监控
     * STOPPED : 有资源，无监控
     * DESTROYED : 无资源，无监控，至少初始化过一次
     * 
     * NEW -> INITED
     * INITED -> RUNNING
     * INITED -> DESTROYED
     * RUNNING -> STOPPED
     * RUNNING -> DESTROYED
     * STOPPED -> DESTROYED
     * DESTROYED -> INITED
     *
     * @author Gan
     * @date 2018年2月5日 上午10:29:24
     * @version 1.0
     *
     */
    public enum MonitorState {
        NEW, INITED, RUNNING, STOPPED, DESTROYED;
        public boolean noGreaterThan(MonitorState state) {
            return this.ordinal() <= state.ordinal();
        }
        public boolean lessThan(MonitorState state) {
            return this.ordinal() < state.ordinal();
        }
    }
}
