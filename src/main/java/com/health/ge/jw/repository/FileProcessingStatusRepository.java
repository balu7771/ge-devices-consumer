package com.health.ge.jw.repository;

import com.health.ge.jw.entity.FileProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileProcessingStatusRepository extends JpaRepository<FileProcessingStatus, Long> {

    @Query(value = "SELECT * FROM public.file_processing_status WHERE file_name = :fileName", nativeQuery = true)
    FileProcessingStatus findByFileName(String fileName);
}
