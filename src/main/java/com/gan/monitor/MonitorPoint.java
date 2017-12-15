/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.monitor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent.Kind;

/**
 *
 *
 * @author Gan
 * @date 2017年12月14日 下午5:30:02
 * @version 1.0
 *
 */
public class MonitorPoint {
    
    private Path path;
    private Kind<?>[] interestOps;
    private IWatchEventHandler handler;
    
    public MonitorPoint(String path, Kind<?>[] interestOps, IWatchEventHandler handler) {
        this.path = Paths.get(path);
        this.interestOps = interestOps;
        this.handler = handler;
    }
    
    /********************************************************
     * 对象存取器
     ********************************************************/
    
    public Path getPath() {
        return path;
    }
    public void setPath(Path path) {
        this.path = path;
    }
    public Kind<?>[] getInterestOps() {
        return interestOps;
    }
    public void setInterestOps(Kind<?>[] interestOps) {
        this.interestOps = interestOps;
    }
    public IWatchEventHandler getHandler() {
        return handler;
    }
    public void setHandler(IWatchEventHandler handler) {
        this.handler = handler;
    }
    
}
