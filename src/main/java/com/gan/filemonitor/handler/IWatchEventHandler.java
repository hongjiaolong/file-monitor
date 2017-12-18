/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.filemonitor.handler;

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
    
    void handle(WatchKey key);
    
    default boolean isRunInNewThread() {
        return true;
    }
    
}
