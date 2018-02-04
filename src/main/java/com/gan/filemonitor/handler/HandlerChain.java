/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor.handler;

import java.nio.file.WatchEvent;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 *
 *
 * @author Gan
 * @date 2018年2月2日 上午11:50:20
 * @version 1.0
 *
 */
public class HandlerChain {
    
    private List<Handler> handlers;
    
    public void callHandlers(String path, WatchEvent.Kind<?> event, int count, Map<String, Object> attachment) {
        CompletableFuture.supplyAsync(() -> {
    }
    
}
