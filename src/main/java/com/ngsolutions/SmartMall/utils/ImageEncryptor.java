package com.ngsolutions.SmartMall.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageEncryptor {

    public byte[] EncryptImage(MultipartFile file) throws IOException;

    public String DecryptImage(byte[] image);
}
