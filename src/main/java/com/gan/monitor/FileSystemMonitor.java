/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.monitor;

import java.io.IOException;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.UUID;

/**
 *
 *
 * @author Gan
 * @date 2017年12月14日 下午5:04:27
 * @version 1.0
 *
 */
public class FileSystemMonitor {
    
    private WatchService watchService;
    private Map<String, MonitorPoint> points = null;
    
    public String addMonitorPoint(String key, MonitorPoint point) {
        if (points.containsKey(key)) {
            // WARN LOG
            return null;
        }
        
        points.put(key, point);
        registerMonitorPoint(point);
        return key;
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
    
    private void registerMonitorPoint(MonitorPoint point) {
        try {
            point.getPath().register(watchService, point.getInterestOps());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
