/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package test.util;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 文件/目录操作工具类
 *
 * @author hongjiaolong
 * @date 2017年12月20日 上午7:48:21
 * @version 1.0
 *
 */
public class FileUtil {

    /**
     * 初始化指定目录. 如果目录不存在, 创建目录. 如果目录存在, 清空该目录{@link FileUtil.clean}
     * 
     * @param dir 要初始化的目录
     * @return 返回标准化绝对路径
     * @throws SecurityException - In the case of the default provider, the
     *             SecurityManager.checkRead(String) is invoked to check read access
     *             to the file. Or the checkWrite method is invoked prior to
     *             attempting to create a directory and its checkRead is invoked for
     *             each parent directory that is checked.
     * 
     * 
     */
    public static Path init(Path dir) throws IOException {
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        } else {
            FileUtil.cleanDirectory(dir);
        }
        return Paths.get("src/test/resources").toRealPath();
    }

    public static void delete(Path path) throws IOException {
        try {
            Files.deleteIfExists(path);
        } catch (DirectoryNotEmptyException e) {
            Files.walkFileTree(path, new FileVisitorForDelete());
        }
    }

    /**
     * 清空指定的目录. 
     * 
     * <p> 如果 {@code path} 是一个普通文件, 该方法不做任何事. 
     * 
     * <p> {@code options} 参数用于指示如何处理符号链接. 默认情况下, 处理符号链接所指向的目标路径. 如果设置为
     * {@link LinkOption#NOFOLLOW_LINKS NOFOLLOW_LINKS}, 代表仅当作链接文件处理. 
     * 
     * @param path
     * @param options
     * @throws IOException
     */
    public static void cleanDirectory(Path path, LinkOption... options) throws IOException {
        if (Files.isDirectory(path, options)) {
            Files.walkFileTree(path, new FileVisitorForClean(path));
        }
    }

    private static class FileVisitorForDelete extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            FileVisitResult result = super.visitFile(file, attrs);
            Files.delete(file);
            return result;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            FileVisitResult result = super.postVisitDirectory(dir, exc);
            Files.delete(dir);
            return result;
        }
    }

    private static class FileVisitorForClean extends SimpleFileVisitor<Path> {
        Path rootPath;

        FileVisitorForClean(Path rootPath) {
            this.rootPath = rootPath;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            FileVisitResult result = super.visitFile(file, attrs);
            Files.delete(file);
            return result;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            FileVisitResult result = super.postVisitDirectory(dir, exc);
            if (!rootPath.equals(dir)) {
                Files.delete(dir);
            }
            return result;
        }
    }

}
