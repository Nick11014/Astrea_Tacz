package com.tacz.guns.api.resource;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.tacz.guns.GunMod;
import com.tacz.guns.util.TacPathVisitor;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œjsonÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â Ã‚Â½ÃƒÂ¨Ã‚Â±Ã‚Â¡ÃƒÂ§Ã‚Â±Ã‚Â»<br/>
 * @deprecated ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â Ã‚ÂconfigÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¯Ã‚Â·ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨<br/>
 * ÃƒÂ¤Ã‚Â»Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¿Ã‚ÂÃƒÂ§Ã¢â‚¬Â¢Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¾Ã¢â‚¬ÂºÃƒÂ¦Ã¢â‚¬â€Ã‚Â§ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ‚Â§ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨<br/>
 * @param <T> ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
 */
@Deprecated
@ApiStatus.Internal
public abstract class JsonResourceLoader<T> {
    private final Marker marker;
    private final Pattern pattern;
    private final String domain;

    private final Class<T> dataClass;

    public JsonResourceLoader(Class<T> dataClass, String marker, String domain) {
        this.dataClass = dataClass;
        this.marker = MarkerManager.getMarker(marker);
        this.domain = domain;
        this.pattern = Pattern.compile("^(\\w+)/" + domain + "/([\\w/]+)\\.json$");
    }

    public Class<T> getDataClass() {
        return dataClass;
    }

    public boolean load(ZipFile zipFile, String zipPath) {
        Matcher matcher = pattern.matcher(zipPath);
        if (matcher.find()) {
            String namespace = matcher.group(1);
            String path = matcher.group(2);
            ZipEntry entry = zipFile.getEntry(zipPath);
            if (entry == null) {
                GunMod.LOGGER.warn(marker, "{} file don't exist", zipPath);
                return false;
            }
            try (InputStream stream = zipFile.getInputStream(entry)) {
                ResourceLocation registryName = ResourceLocation.fromNamespaceAndPath(namespace, path);
                String json = IOUtils.toString(stream, StandardCharsets.UTF_8);
                resolveJson(registryName, json);
                return true;
            } catch (IOException | JsonSyntaxException | JsonIOException exception) {
                GunMod.LOGGER.warn(marker, "Failed to read file: {}, entry: {}", zipFile, entry);
                exception.printStackTrace();
            }
        }
        return false;
    }

    public void load(File root) {
        Path filePath = root.toPath().resolve(domain);
        if (Files.isDirectory(filePath)) {
            TacPathVisitor visitor = new TacPathVisitor(filePath.toFile(), root.getName(), ".json", (id, file) -> {
                try (InputStream stream = Files.newInputStream(file)) {
                    String json = IOUtils.toString(stream, StandardCharsets.UTF_8);
                    resolveJson(id, json);
                } catch (IOException | JsonSyntaxException | JsonIOException exception) {
                    GunMod.LOGGER.warn(marker, "Failed to read file: {}", file);
                    exception.printStackTrace();
                }
            });
            try {
                Files.walkFileTree(filePath, visitor);
            } catch (IOException e) {
                GunMod.LOGGER.warn(marker, "Failed to walk file tree: {}", filePath);
                e.printStackTrace();
            }
        }
    }

    public abstract void resolveJson(ResourceLocation id, String json);
}































































