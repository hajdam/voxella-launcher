// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.exbin.voxella.launcher.game.terasology.util;

import java.util.logging.Logger;
import org.exbin.voxella.launcher.game.terasology.util.visitor.ArchiveCopyVisitor;
import org.exbin.voxella.launcher.game.terasology.util.visitor.DeleteFileVisitor;
import org.exbin.voxella.launcher.game.terasology.util.visitor.LocalCopyVisitor;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.stream.Stream;
import org.exbin.voxella.launcher.api.utils.LoggerUtils;

public final class FileUtils {

    private static final Logger logger = Logger.getLogger(FileUtils.class.getName());

    private FileUtils() {
    }

    /**
     * Deletes the specified file or directory. If the path is a directory it is
     * removed recursively.
     *
     * @param path Path to delete
     * @throws IOException if something goes wrong
     */
    public static void delete(final Path path) throws IOException {
        Files.walkFileTree(path, new DeleteFileVisitor());
    }

    /**
     * Deletes the specified file. If deletion of the file fails or a exception
     * happens it will be logged. Directories are not handled by this deletion.
     *
     * @param file File to delete
     */
    public static void deleteFileSilently(final Path file) {
        try {
            final boolean deleted = Files.deleteIfExists(file);
            if (!deleted) {
                LoggerUtils.log(logger, Level.SEVERE, "Could not silently delete file '{0}'!", file);
            }
        } catch (IOException e) {
            LoggerUtils.log(logger, Level.SEVERE, "Could not silently delete file '{0}'!", e, file);
        }
    }

    /**
     * Deletes the content of the directory without removing the directory
     * itself.
     *
     * @param directory Directory which content has to be deleted
     * @throws IOException if something goes wrong
     */
    static void deleteDirectoryContent(final Path directory) throws IOException {
        try ( Stream<Path> stream = Files.list(directory)) {
            stream.forEach(path -> {
                try {
                    Files.walkFileTree(path, new DeleteFileVisitor());
                } catch (IOException e) {
                    LoggerUtils.log(logger, Level.SEVERE, "Failed to delete '{0}'", e, path);
                }
            });
        }
    }

    /**
     * Extracts the specified ZIP file to the specified location.
     *
     * @param archive the ZIP file to extract
     * @param outputLocation where to extract to
     * @return true if successful
     */
    public static boolean extractZipTo(final Path archive, final Path outputLocation) {
        LoggerUtils.log(logger, Level.SEVERE, "Extracting '{0}' to '{1}'", archive, outputLocation);

        try {
            if (Files.notExists(outputLocation)) {
                Files.createDirectories(outputLocation);
            }
            try ( FileSystem fileSystem = FileSystems.newFileSystem(archive, ((ClassLoader) null))) {
                for (Path rootDirectory : fileSystem.getRootDirectories()) {
                    Files.walkFileTree(rootDirectory, new ArchiveCopyVisitor(outputLocation));
                }
            }
            return true;
        } catch (IOException e) {
            LoggerUtils.log(logger, Level.SEVERE, "Could not extract zip archive '{0}' to '{1}'!", e, archive, outputLocation);
            return false;
        }
    }

    /**
     * Copy the whole folder recursively to the specified destination.
     *
     * @param source the folder to copy
     * @param destination where to copy to
     * @throws IOException if copying fails
     */
    public static void copyFolder(final Path source, final Path destination) throws IOException {
        if (Files.notExists(source)) {
            LoggerUtils.log(logger, Level.SEVERE, "Source file doesn't exists! '{0}'", source);
            return;
        }

        Files.walkFileTree(source, new LocalCopyVisitor(source, destination));
    }

    /**
     * Ensures that a directory exists, is a directory and is empty.
     *
     * Deletes directory contents if the directory is not empty. If the
     * directory does not exist, it is created. The directory itself is not
     * deleted.
     *
     * @param directory absolute path to the directory
     *
     * @throws IOException The path is not a directory
     */
    public static void ensureEmptyDir(final Path directory) throws IOException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        } else {

            if (!Files.isDirectory(directory)) {
                throw new IOException("Directory is not a directory! " + directory);
            } else {
                deleteDirectoryContent(directory);
            }
        }
    }

    /**
     * Checks whether the given path exists and is a readable directory.
     *
     * Will return <code>false</code> if the given path is <code>null</code> or
     * any of the required checks fails.
     *
     * @param path path to the directory to check (may be null)
     * @return true if the given path is a directory with read permissions,
     * false otherwise
     */
    public static boolean isReadableDir(final Path path) {
        return path != null
                && Files.exists(path)
                && Files.isDirectory(path)
                && Files.isReadable(path);
    }

    /**
     * Checks if the given path exists, is a directory and can be read and
     * written by the program.
     *
     * @param directory absolute path to the directory
     *
     * @throws IOException Reading the path fails in some way
     */
    public static void ensureWritableDir(Path directory) throws IOException {

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        if (!Files.isDirectory(directory)) {
            throw new IOException("Not a directory: " + directory);
        }

        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IOException("Missing read or write permissions: " + directory);
        }
    }
}
