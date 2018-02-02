import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

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
        Path path = Paths.get("E:/workspace/afa-parent");
        Path path2 = Paths.get("../afa-parent");
        System.out.println(path2.toAbsolutePath().normalize());
        System.out.println(Files.isSameFile(path, path2));
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
