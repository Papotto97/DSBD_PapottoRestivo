package com.unict.auctionmanager.config.quartz;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

public class ResourceReader {

    private static final String UTF_8 = "UTF-8";

	public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
	
	
	public static String readFileToString(String path) {
	    ResourceLoader resourceLoader = new DefaultResourceLoader();
	    Resource resource = resourceLoader.getResource(path);
	    return asString(resource);
	}

}