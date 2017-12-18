/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.filemonitor.handler;

import java.nio.file.WatchEvent;
import java.nio.file.Watchable;
import java.util.List;

/**
 * 
 *
 * @author hongjiaolong
 * @date 2017年12月18日 下午7:56:09
 * @version 1.0
 *
 */
public class DefaultWatchEventHandler extends AbstractWatchEventHandler {
    
    @Override
    protected void handle(Watchable watchable, List<WatchEvent<?>> events) {
        for (WatchEvent<?> event : events) {
            System.out.println(event.context() + " comes to " + event.kind());
        }
    }

}
