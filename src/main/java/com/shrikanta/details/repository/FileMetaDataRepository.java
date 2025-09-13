package com.shrikanta.details.repository;

import com.shrikanta.details.pojo.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileMetaDataRepository extends JpaRepository<FileMetaData,Long> {
    Optional<FileMetaData> findByChecksum(String checksum);

    Optional<FileMetaData> findByFilename(String filename);
}
