package com.tacz.guns.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tacz.guns.GunMod;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class GetJarResources {
    /**
     * ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¥Ã‚Â½Ã‚Â±ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â¹ÃƒÂ§Ã‚Â¼Ã‚Â©ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Å“Ã‹â€ ÃƒÂ¥Ã‚Â¸Ã…â€™ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¦Ã…â€™Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
     * <p>
     * ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã‚Âº TaCZ ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ§Ã‚Â¬Ã¢â‚¬ÂÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¤Ã‚ÂºÃ‚Â¤ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
     */
    private static final Instant BACKUP_TIME = Instant.parse("2024-02-26T12:28:08.000Z");
    private static final Path BACKUP_PATH = Paths.get("config", GunMod.MOD_ID, "backup");
    private static final SimpleDateFormat BACKUP_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");
    private static final int MAX_BACKUP_COUNT = 10;

    private GetJarResources() {
    }

    /**
     * ÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¦Ã…â€™Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¼Ã‚ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â¦Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬ÂºÃ¢â‚¬â€œÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param srcPath jar ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¥Ã‚ÂÃ¢â€šÂ¬
     * @param root    ÃƒÂ¦Ã†â€™Ã‚Â³ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¥Ã‚Â½Ã¢â‚¬Â¢
     * @param path    ÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
     */
    public static void copyModFile(String srcPath, Path root, String path) {
        URL url = GunMod.class.getResource(srcPath);
        try {
            if (url != null) {
                FileUtils.copyURLToFile(url, root.resolve(path).toFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¦Ã…â€™Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¼Ã‚ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â¦Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬ÂºÃ¢â‚¬â€œÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param srcPath jar ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¥Ã‚ÂÃ¢â€šÂ¬
     * @param root    ÃƒÂ¦Ã†â€™Ã‚Â³ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¥Ã‚Â½Ã¢â‚¬Â¢
     * @param path    ÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
     */
    public static void copyModDirectory(Class<?> resourceClass, String srcPath, Path root, String path) {
        URL url = resourceClass.getResource(srcPath);
        try {
            if (url != null) {
                copyFolder(url.toURI(), root.resolve(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¦Ã…â€™Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¼Ã‚ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â¦Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬ÂºÃ¢â‚¬â€œÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param srcPath jar ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¥Ã‚ÂÃ¢â€šÂ¬
     * @param root    ÃƒÂ¦Ã†â€™Ã‚Â³ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¥Ã‚Â½Ã¢â‚¬Â¢
     * @param path    ÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
     */
    public static void copyModDirectory(String srcPath, Path root, String path) {
        copyModDirectory(GunMod.class, srcPath, root, path);
    }

    @Nullable
    public static InputStream readModFile(String filePath) {
        URL url = GunMod.class.getResource(filePath);
        try {
            if (url != null) {
                return url.openStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFolder(URI sourceURI, Path targetPath) throws IOException {
        if (Files.isDirectory(targetPath)) {
            backupFiles(targetPath);
            deleteFiles(targetPath);
        }
        try (Stream<Path> stream = Files.walk(Paths.get(sourceURI), Integer.MAX_VALUE)) {
            stream.forEach(source -> {
                Path target = targetPath.resolve(sourceURI.relativize(source.toUri()).toString());
                try {
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(target);
                    } else {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void backupFiles(Path targetPath) throws IOException {
        String dirName = targetPath.getFileName().toString();
        Path resourcePacksPath = FMLPaths.GAMEDIR.get().resolve("tacz_backup");
        Path backupPath = resourcePacksPath.resolve(dirName);
        if (!Files.isDirectory(backupPath)) {
            Files.createDirectories(backupPath);
        }

        Set<String> cacheMd5 = checkOldBackups(backupPath);

        File tempFile = File.createTempFile(dirName, ".tmp");
        FileTime fileTime = FileTime.from(BACKUP_TIME);

        try (ZipOutputStream zs = new ZipOutputStream(new FileOutputStream(tempFile));
             Stream<Path> fileWalks = Files.walk(targetPath)) {
            fileWalks.filter(Files::isRegularFile).forEach(path -> {
                String entryPath = targetPath.relativize(path).toString();
                ZipEntry zipEntry = new ZipEntry(entryPath);
                zipEntry.setLastModifiedTime(fileTime);
                try {
                    zs.putNextEntry(zipEntry);
                    Files.copy(path, zs);
                    zs.closeEntry();
                } catch (IOException e) {
                    GunMod.LOGGER.info("Error in zip file: {}", e.getMessage());
                }
            });
        }

        try (FileInputStream inputStream = new FileInputStream(tempFile)) {
            String md5Hex = Md5Utils.md5Hex(inputStream);
            if (cacheMd5.contains(md5Hex)) {
                tempFile.deleteOnExit();
            } else {
                String dataName = BACKUP_DATE_FORMAT.format(new Date()).toLowerCase(Locale.ENGLISH);
                Path backupZipFilePath = backupPath.resolve(String.format("backup-%s-%s.zip", dataName, md5Hex));
                FileUtils.copyFile(tempFile, backupZipFilePath.toFile());
            }
        }
    }

    private static Set<String> checkOldBackups(Path backupPath) {
        Set<String> allMd5Hex = Sets.newHashSet();
        if (!Files.isDirectory(backupPath)) {
            return allMd5Hex;
        }
        try {
            List<File> delFiles = Lists.newArrayList(FileUtils.listFiles(backupPath.toFile(), TrueFileFilter.TRUE, null));
            delFiles.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int count = 1;
            for (File file : delFiles) {
                if (count >= MAX_BACKUP_COUNT) {
                    GunMod.LOGGER.info("Deleting old backup gun pack {}", file.getName());
                    FileUtils.deleteQuietly(file);
                } else {
                    try (FileInputStream inputStream = new FileInputStream(file)) {
                        allMd5Hex.add(Md5Utils.md5Hex(inputStream));
                    }
                }
                count++;
            }
        } catch (Exception exception) {
            GunMod.LOGGER.error("Error while checking old backup gun pack : {}", exception.getMessage());
        }
        return allMd5Hex;
    }

    private static void deleteFiles(Path targetPath) throws IOException {
        Files.walkFileTree(targetPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}































































