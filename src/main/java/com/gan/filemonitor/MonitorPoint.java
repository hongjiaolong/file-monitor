/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import java.nio.file.WatchEvent;

import com.gan.filemonitor.handler.IWatchEventHandler;

/**
 *
 *
 * @author Gan
 * @date 2017年12月14日 下午5:30:02
 * @version 1.0
 *
 */
public class MonitorPoint {
    
    private String path;
    private WatchEvent.Kind<?>[] interestOps;
    private IWatchEventHandler handler;
    
    public MonitorPoint(String path, WatchEvent.Kind<?>[] interestOps, IWatchEventHandler handler) {
        this.path = path;
        this.interestOps = interestOps;
        this.handler = handler;
    }
    
    /********************************************************
     * 对象存取器
     ********************************************************/
    
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public WatchEvent.Kind<?>[] getInterestOps() {
        return interestOps;
    }
    public void setInterestOps(WatchEvent.Kind<?>[] interestOps) {
        this.interestOps = interestOps;
    }
    public IWatchEventHandler getHandler() {
        return handler;
    }
    public void setHandler(IWatchEventHandler handler) {
        this.handler = handler;
    }
    
}
