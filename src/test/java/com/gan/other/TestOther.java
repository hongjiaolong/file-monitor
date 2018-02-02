/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.other;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.junit.jupiter.api.Test;

/**
 * 
 *
 * @author hongjiaolong
 * @date 2017年12月18日 下午9:40:33
 * @version 1.0
 *
 */
class TestOther {
    
    @Test
    void testCurrentPath() throws Exception {
        System.out.println(Paths.get("").toRealPath());
    }
    
    @Test
    void testResourcesInCurrentPath() throws Exception {
        System.out.println(Paths.get("src/main/resources/logback.xml").toRealPath());
    }
    
    public static void main(String[] args) throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get("E:\\aaa").register(watchService, 
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.ENTRY_DELETE);
        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(event.context() + " " + event.kind());
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }
    
}
