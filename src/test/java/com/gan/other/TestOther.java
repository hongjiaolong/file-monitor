/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.other;

import java.nio.file.Paths;

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
    
}
