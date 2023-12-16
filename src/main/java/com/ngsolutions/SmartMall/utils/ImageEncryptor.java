package com.ngsolutions.SmartMall.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageEncryptor {

    byte[] EncryptImage(MultipartFile file) throws IOException;

    String DecryptImage(byte[] image);

    MultipartFile DecryptImageAsMultipartFile(byte[] fileBytes) throws IOException;
}
