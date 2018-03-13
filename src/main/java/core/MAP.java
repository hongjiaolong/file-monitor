/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author Gan
 * @date 2018年2月5日 下午1:39:20
 * @version 1.0
 *
 */
public class MAP<K1, K2, V> {
    
    private Map<K1, K2> m1 = new ConcurrentHashMap<>();
    private Map<K2, V> m2 = new ConcurrentHashMap<>();
    
    public String generateKey() {};
    
    
    public void put(K key, J join, V value) {
        m1.put(key, join);
        m2.put(join, value);
    }
    
    public V get(K key, String identity) {
        Map<String, V> m2 = map.get(key);
        if (m2 != null) {
            return m2.get(identity);
        }
        return null;
    }
    
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }
    
}
