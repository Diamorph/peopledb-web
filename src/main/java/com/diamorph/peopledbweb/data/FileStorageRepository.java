package com.diamorph.peopledbweb.data;

import com.diamorph.peopledbweb.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class FileStorageRepository {

    @Value("${STORAGE_FOLDER}")
    private String storageFolder;
    public void save(String originalFilename, InputStream inputStream) {
        try {
            Path filePath = getPath(originalFilename);
            Files.copy(inputStream, filePath);
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    private Path getPath(String originalFilename) {
        return Path.of(storageFolder).resolve(originalFilename).normalize();
    }

    public Resource findByName(String filename) {
        try {
            Path filePath = getPath(filename);
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new StorageException(e);
        }
    }

    public void deleteAllByName(Collection<String> filenames) {
        try {
            for (String filename: filenames.stream().filter(Objects::nonNull).collect(Collectors.toSet())) {
                Path filePath = getPath(filename);
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }
}
