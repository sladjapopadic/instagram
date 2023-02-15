package com.itengine.instagram.image;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {

    public byte[] getDefaultProfileImage() throws IOException {
        File file = new ClassPathResource("instagram_user.png").getFile();
        return Files.readAllBytes(file.toPath());
    }
}
