/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.monitor;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.List;

/**
 * 
 *
 * @author hongjiaolong
 * @date 2017年12月14日 下午7:48:45
 * @version 1.0
 *
 */
public interface IWatchEventHandler {
    
    void handle();
    
    default boolean isRunInNewThread() {
        return true;
    }
    
    default void handle(WatchKey key) {
        List<WatchEvent<?>> events = key.pollEvents();
        
        for (WatchEvent<?> event : events) {
            System.out.println(event.context() + " comes to " + event.kind());
        }
        
        if (!key.reset()) {
            
        }
    }
}
