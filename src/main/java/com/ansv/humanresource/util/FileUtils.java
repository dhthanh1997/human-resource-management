package com.ansv.humanresource.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@NoArgsConstructor
public class FileUtils {

    public static File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File newFile = new File(file.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(newFile);
        outputStream.write(file.getBytes());
        outputStream.close();
        return newFile;
    }
}
