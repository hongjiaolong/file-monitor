/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import java.nio.file.WatchEvent;
import java.util.List;

import com.gan.filemonitor.handler.HandlerChain;
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
    
    private String name;
    private String path;
    private WatchEvent.Kind<?>[] interestOps;
    private List<HandlerChain> handlerChains;
    
    private boolean recursion;
    private boolean ignoreDirectory;
    private boolean exclusiveService;
    private boolean inSameService;
    
    public boolean isFile() {
        return false;
    }
    
    public boolean isDirectory() {
        return !isFile();
    }
    
    @Override
    public String toString() {
        return "MonitorPoint[name=" + name + "]";
    }
    
    /********************************************************
     * setter / getter
     ********************************************************/
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
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

    public List<HandlerChain> getHandlerChains() {
        return handlerChains;
    }

    public void setHandlerChains(List<HandlerChain> handlerChains) {
        this.handlerChains = handlerChains;
    }
    
}
