package com.example.hello.service.impl;

import com.example.hello.service.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioClient minioClient;

    @Override
    public String upload(MultipartFile file) {
        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件扩展名
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构建新的文件名
            String objectName = UUID.randomUUID().toString() + extension;
            
            // 上传文件到 MinIO
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket("bp3txer3-zxyf")
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            
            // 返回文件访问路径
            return "https://objectstorageapi.bja.sealos.run/bp3txer3-zxyf/" + objectName;
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }
}
