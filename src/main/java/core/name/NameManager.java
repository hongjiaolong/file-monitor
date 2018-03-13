/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package core.name;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 命名管理器
 *
 * @author Gan
 * @date 2018年2月2日 上午10:40:29
 * @version 1.0
 *
 */
public abstract class NameManager<T> {
    
    private Map<String, T> nameTables = new ConcurrentHashMap<>();
    
    protected abstract String generateName(T t) throws GenerateNameException;
    
    public boolean exists(String name) {
        return nameTables.containsKey(name);
    }
    
    public boolean register(String name, T t) {
        if (!exists(name)) {
            return nameTables.putIfAbsent(name, t) == null;
        }
        return false;
    }
    
    public boolean register(T t) throws GenerateNameException {
        return register(generateName(t), t);
    }
    
    public T cancel(String name) {
        return nameTables.remove(name);
    }
    
    public Collection<T> getAllNameObjects() {
        return nameTables.values();
    }
}
