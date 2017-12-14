/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.monitor;

import java.util.Map;

/**
 *
 *
 * @author Gan
 * @date 2017年12月14日 下午5:04:27
 * @version 1.0
 *
 */
public class FileSystemMonitor {
    
    private Map<String, MonitorPoint> points = null;
    
    public static FileSystemMonitor getInstance() {
        return null;
    }
    
    public String addMonitorPoint(MonitorPoint point) {
        return null;
    }
    
    public void removeMonitorPoint(String key) {
        
    }
    
    public void modifyMonitorPoint(String key, MonitorPoint newPoint) {
        
    }
    
    public Map<String, MonitorPoint> getMonitorPoints() {
        return null;
    }
    
}
