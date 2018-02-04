/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import java.nio.file.WatchEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private List<HandlerChainWrapper> handlerChainWrappers;
    
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
     * setter / getter （变体）
     ********************************************************/
    
    public void addHandlerChain(HandlerChain handlerChain) {
        this.addHandlerChain(handlerChain, true);
    }
    public void addHandlerChain(HandlerChain handlerChain, boolean runInSeperatedThread) {
        this.handlerChainWrappers.add(new HandlerChainWrapper(handlerChain, runInSeperatedThread));
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
    public boolean isRecursion() {
        return recursion;
    }
    public void setRecursion(boolean recursion) {
        this.recursion = recursion;
    }
    public boolean isIgnoreDirectory() {
        return ignoreDirectory;
    }
    public void setIgnoreDirectory(boolean ignoreDirectory) {
        this.ignoreDirectory = ignoreDirectory;
    }
    public boolean isExclusiveService() {
        return exclusiveService;
    }
    public void setExclusiveService(boolean exclusiveService) {
        this.exclusiveService = exclusiveService;
    }
    public boolean isInSameService() {
        return inSameService;
    }
    public void setInSameService(boolean inSameService) {
        this.inSameService = inSameService;
    }
    
    public static class HandlerChainWrapper {
        private HandlerChain handlerChain;
        private boolean runInSeperatedThread;
        private HandlerChainWrapper(HandlerChain handlerChain, boolean runInSeperatedThread) {
            this.handlerChain = handlerChain;
            this.runInSeperatedThread = runInSeperatedThread;
        }
        public HandlerChain getHandlerChain() {
            return handlerChain;
        }
        public boolean isRunInSeperatedThread() {
            return runInSeperatedThread;
        }
    }
    
}
