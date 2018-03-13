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
public class MAP2<K, V> {
    
    private Map<K, Map<String, V>> map = new ConcurrentHashMap<>();
    
    public void put(K key, String identity, V value) {
        if (!map.containsKey(key)) {
            map.putIfAbsent(key, new ConcurrentHashMap<>());
        }
        map.get(key).put(identity, value);
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
