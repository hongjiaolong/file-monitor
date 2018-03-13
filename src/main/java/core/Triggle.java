/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package core;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gan.filemonitor.MonitorPoint;
import com.gan.filemonitor.PathWatcher;

/**
 *
 *
 * @author Gan
 * @date 2018年2月9日 下午2:59:33
 * @version 1.0
 *
 */
public class Triggle<T1, T2, T3> {
    
    // 哪个监视点的哪些路径被注册到了哪个PathWatcher上
    private Triggle<MonitorPoint, List<Path>, PathWatcher> recorder;
    
    private Map<MonitorPoint, Wrapper> wrappers1;
    private Map<List<Path>, Wrapper> wrappers2;
    private Map<PathWatcher, Wrapper> wrappers3;
    private class Wrapper {
        MonitorPoint point;
        List<Path> paths;
        PathWatcher watcher;
        public Wrapper(MonitorPoint point, List<Path> paths, PathWatcher watcher) {
            this.point = point;
            this.paths = paths;
            this.watcher = watcher;
        }
    }
    
    void put(MonitorPoint point, List<Path> paths, PathWatcher watcher) {
        Wrapper wrapper = new Wrapper(point, paths, watcher);
        wrappers1.put(point, wrapper);
        wrappers2.put(paths, wrapper);
        wrappers3.put(watcher, wrapper);
    }
    
    Set<MonitorPoint> getMonitorPoints() {
        return wrappers1.keySet();
    }
    List<Path> get(MonitorPoint point) {
        return wrappers1.get(point).;
    }
    
    private Map<T1, Collection<T2>> m12;
    private Map<T1, Collection<T3>> m13;
    private Map<T2, Collection<T1>> m21;
    private Map<T2, Collection<T3>> m23;
    private Map<T3, Collection<T1>> m31;
    private Map<T3, Collection<T2>> m32;
    
    public Collection<T2> getSecondsByFirst(T1 t1) {
        return m12.get(t1);
    }
    public void putSecondsByFirst(T1 t1, T2 t2) {
        m12.get(t1).add(t2);
        m21.get(t2).add(t1);
    }
    
}
