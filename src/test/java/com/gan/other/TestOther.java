/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.other;

import java.io.IOException;
import java.nio.file.Paths;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

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
    
    public static void main(String[] args) throws IOException {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = javac.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects("src/main/java/com/gan/filemonitor/handler/IWatchEventHandler.java");
        CompilationTask task = javac.getTask(null, manager, null, null, null, it);
        task.call();
        manager.close();
    }
    
}
