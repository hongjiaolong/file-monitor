/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.filemonitor.handler;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.Watchable;
import java.util.List;

/**
 * 
 *
 * @author hongjiaolong
 * @date 2017年12月18日 下午7:52:20
 * @version 1.0
 *
 */
public abstract class AbstractWatchEventHandler implements IWatchEventHandler {
    
    @Override
    public void handle(WatchKey key) {
        handle(key.watchable(), key.pollEvents());
        
        if (!key.reset()) {
            
        }
    }
    
    protected abstract void handle(Watchable watchable, List<WatchEvent<?>> events);

}
