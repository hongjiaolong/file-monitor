/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;

import com.gan.filemonitor.handler.HandlerChain;

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
    
    private MonitorMode monitorMode;
    private HandlerChainMode handlerChainMode;
    
    public boolean isFile() {
        return Files.isRegularFile(Paths.get(path));
    }
    
    public boolean isDirectory() {
        return Files.isDirectory(Paths.get(path));
    }
    
    public Path getAbsoluteMonitorPath() {
        return path == null ? null : Paths.get(path).toAbsolutePath().normalize();
    }
    
    /**
     * 返回所有用于监视器注册的路径
     */
    public List<Path> getRegisterPaths() {
        List<Path> paths = new ArrayList<>();
        if (isFile()) {
            paths.add(getAbsoluteMonitorPath().getParent());
        } else if (isDirectory()) {
            if (recursion) {
                paths.add(getAbsoluteMonitorPath().getParent());
            }
        }
        return paths;
    }
    
    /**
     * 返回所有被监视的路径
     */
    public List<Path> getAllMonitoredPaths() {
        List<Path> paths = new ArrayList<>();
        if (isFile()) {
            paths.add(Paths.get(path));
        } else if (isDirectory()) {
            
        }
        return paths;
    }
    
    @Override
    public String toString() {
        return "MonitorPoint[name=" + name + "]";
    }
    
    /********************************************************
     * setter / getter （变体）
     ********************************************************/
    
    
    
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
    public void addHandlerChain(HandlerChain handlerChain) {
        this.handlerChains.add(handlerChain);
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
    public MonitorMode getMonitorMode() {
        return monitorMode;
    }
    public void setMonitorMode(MonitorMode monitorMode) {
        this.monitorMode = monitorMode;
    }
    public HandlerChainMode getHandlerChainMode() {
        return handlerChainMode;
    }
    public void setHandlerChainMode(HandlerChainMode handlerChainMode) {
        this.handlerChainMode = handlerChainMode;
    }
    
    public static enum MonitorMode {
        NORMAL, INSEPARABLE, EXCLUSIVE
    }
    public static enum HandlerChainMode {
        RUN_IN_CURRENT_THREAD, RUN_IN_SEPERATED_THREAD
    }
}
