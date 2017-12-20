/*
 * Copyright(C) 2013 Xxx Corporation. All rights reserved.
 * 
 * Contributors:
 *     Xxx Corporation - initial API and implementation
 */
package com.gan.test.util;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
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
    
    public static void delete(Path path) throws IOException {
        try {
            Files.deleteIfExists(path);
        } catch (DirectoryNotEmptyException e) {
            Files.walkFileTree(path, new FileVisitorForDelete());
        }
    }
    
    public static void cleanDirectory(Path dir) throws IOException {
        if (Files.isDirectory(dir)) {
            Files.walkFileTree(dir, new FileVisitorForClean(dir));
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
