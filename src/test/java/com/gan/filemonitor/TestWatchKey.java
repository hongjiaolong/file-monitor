/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.filemonitor;

import static java.nio.file.StandardWatchEventKinds.*;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 *
 * @author hongjiaolong
 * @date 2017年12月18日 下午8:58:47
 * @version 1.0
 *
 */
class TestWatchKey {
    
    @Test
    void testCancel() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get("src/test/resources").register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(event.context() + " " + event.kind());
            }
            key.reset();
            key.cancel();
        }
    }
    
    @Test
    void testSinglePath() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get("src/test/resources").register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        while (true) {
            WatchKey key = watchService.take();
            System.out.print("K: " + key.watchable() + "\\-");
            List<WatchEvent<?>> es = key.pollEvents();
            for (int i = 0; i < es.size(); i++) {
                if (i == 0) {
                    System.out.print(es.get(i).context() + " ");
                }
                System.out.print(es.get(i).kind() + " ");
                if (i == es.size() - 1) {
                    System.out.println();
                }
            }
            key.reset();
        }
    }
    
    @Test
    void testMultiPath() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get("src/test/resources").register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        Paths.get("src/test/resources/a").register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        while (true) {
            WatchKey key = watchService.take();
            System.out.print("K: " + key.watchable() + "\\-");
            List<WatchEvent<?>> es = key.pollEvents();
            for (int i = 0; i < es.size(); i++) {
                if (i == 0) {
                    System.out.print(es.get(i).context() + " ");
                }
                System.out.print(es.get(i).kind() + " ");
                if (i == es.size() - 1) {
                    System.out.println();
                }
            }
            
            key = watchService.take();
            System.out.print("K: " + key.watchable() + "\\-");
            es = key.pollEvents();
            for (int i = 0; i < es.size(); i++) {
                if (i == 0) {
                    System.out.print(es.get(i).context() + " ");
                }
                System.out.print(es.get(i).kind() + " ");
                if (i == es.size() - 1) {
                    System.out.println();
                }
            }
            key.reset();
            if (key.watchable().equals(Paths.get("src/test/resources"))) {
                System.out.println("src/test/resources");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(1000);
                                System.out.print("+");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            } else if (key.watchable().equals(Paths.get("src/test/resources/a"))) {
                System.out.println("src/test/resources/a");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(1000);
                                System.out.print("-");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
            
        }
    }
    
}