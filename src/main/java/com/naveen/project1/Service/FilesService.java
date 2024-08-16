package com.naveen.project1.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesService {
    String uploadImage(String path, MultipartFile file) throws IOException;
}
