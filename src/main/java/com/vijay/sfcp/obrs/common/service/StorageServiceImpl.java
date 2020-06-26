package com.vijay.sfcp.obrs.common.service;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 22 June 2020
*/

import com.vijay.sfcp.obrs.common.dto.StorageProperties;
import com.vijay.sfcp.obrs.common.utils.CommonUtil;
import com.vijay.sfcp.obrs.common.utils.LogUtil;
import com.vijay.sfcp.obrs.error.exceptions.FileStorageException;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            LogUtil.logDebug(LOG,CLASS_NAME,"init","directory created");
        } catch (IOException e) {
            throw new FileStorageException("Could not initialize storage location");
        }
    }

    @Override
    public String store(String rename, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        LogUtil.logDebug(LOG,CLASS_NAME,"store","filename = "+filename);
        filename = filename.toLowerCase().replaceAll(" ", "-");
        LogUtil.logDebug(LOG,CLASS_NAME,"store","rename = "+rename);
        if(!StringUtils.isEmpty(rename))
            filename = rename+"."+CommonUtil.getExtensionByStringHandling(filename).get();
        LogUtil.logDebug(LOG,CLASS_NAME,"store","updated filename = "+filename);
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new FileStorageException( "Cannot store file with relative path outside current directory "+ filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new FileStorageException("Failed to store file " + filename);
        }

        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new FileStorageException("Failed to read stored files");
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new NotFoundException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new NotFoundException("Could not read file: " + filename);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
