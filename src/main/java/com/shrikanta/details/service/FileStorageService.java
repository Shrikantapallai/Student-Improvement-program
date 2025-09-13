package com.shrikanta.details.service;

import com.shrikanta.details.pojo.FileMetaData;
import com.shrikanta.details.repository.FileMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Service
public class FileStorageService {

    private FileMetaDataRepository fileMetaDataRepository;
    public FileStorageService(){

    }
    @Autowired
    public FileStorageService(FileMetaDataRepository fileMetaDataRepository){
        this.fileMetaDataRepository=fileMetaDataRepository;
    }

    private String getFileChecksum(MultipartFile file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream inputStream = file.getInputStream()) {
            byte[] byteArray = new byte[1024];
            int bytesCount;
            while ((bytesCount = inputStream.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
        }
        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    public String saveFile(MultipartFile file) throws Exception {
        String checksum = getFileChecksum(file);

        if (fileMetaDataRepository.findByChecksum(checksum).isPresent()) {
            return " Duplicate file detected!";
        }

        FileMetaData metadata = new FileMetaData(
                checksum,
                file.getOriginalFilename(),
                file.getSize(),
                LocalDateTime.now()
        );
        metadata.setFileData(file.getBytes());
        fileMetaDataRepository.save(metadata);
        return "file updated successfully :" + file.getOriginalFilename();
    }
    public byte[] downloadFile(String filename) {
        FileMetaData file =  fileMetaDataRepository.findByFilename(filename)
                .orElseThrow(() -> new RuntimeException("File not found"));
        return file.getFileData();
    }

}
