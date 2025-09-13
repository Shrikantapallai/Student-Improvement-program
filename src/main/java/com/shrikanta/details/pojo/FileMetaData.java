package com.shrikanta.details.pojo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name="file_metadata")
public class FileMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 64)
    private String checksum;
    private String filename;
    private long filesize;
    private LocalDateTime uploadDate;
    @Lob
    private byte[] fileData;

    public FileMetaData(){

    }
    public FileMetaData(String checksum, String filename, long filesize, LocalDateTime uploadDate){
        this.checksum = checksum;
        this.filename = filename;
        this.filesize = filesize;
        this.uploadDate = uploadDate;
    }
    public Long getId() { return id; }
    public String getChecksum() { return checksum; }
    public String getFileName() { return filename; }
    public long getFileSize() { return filesize; }
    public LocalDateTime getUploadDate() { return uploadDate; }

    public void setId(Long id) { this.id = id; }
    public void setChecksum(String checksum) { this.checksum = checksum; }
    public void setFileName(String filename) { this.filename = filename; }
    public void setFileSize(long fileSize) { this.filesize = fileSize; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }




    public byte[] getFileData() {
        return this.fileData;
    }

    public void setFileData(byte[] bytes) {
        this.fileData = bytes;
    }
}
