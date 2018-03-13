import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 *
 *
 * @author Gan
 * @date 2018年1月25日 下午4:04:54
 * @version 1.0
 *
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        WatchService service = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("ss");
        System.out.println(path.toAbsolutePath().normalize());
        
        Path path2 = Paths.get("ss2");
        
        path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        path2.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        try {
            WatchKey key = service.take();
            List<WatchEvent<?>> events = key.pollEvents();
            for (WatchEvent<?> e : events) {
                System.out.println(e.context() + " " + e.count() + " " + e.kind());
            }
            if (!key.reset()) {
                System.out.println("HH");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
    }
    
    void f() throws Exception {
        WatchService service = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("");
        
        path.register(service, StandardWatchEventKinds.ENTRY_CREATE);
        
        while (true) {
            WatchKey key = service.take();
            List<WatchEvent<?>> events = key.pollEvents();
            for (WatchEvent<?> e : events) {
                System.out.println(e);
            }
            if (!key.reset()) {
                break;
            }
        }
    }
    
}
