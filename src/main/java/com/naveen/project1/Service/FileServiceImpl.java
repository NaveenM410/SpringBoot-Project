package com.naveen.project1.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FilesService
{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //filename of current original file

        String originalFileName =file.getOriginalFilename();

        //generate a unique filename
        String randomId = UUID.randomUUID().toString();

        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.'))); //mac.jpg --->1234 ---->1234.jpg
        String filepath = path + File.separator + fileName;


        //check if path exists and create
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filepath));

        //return file name
        return fileName;

    }
}
