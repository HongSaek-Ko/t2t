package org.t2t.prd.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.prd.dto.FileDTO;

import java.util.List;

@Mapper
public interface FileMapper {
    void insertFile(FileDTO fileDTO);

    FileDTO selectFile(Long prdId);

    void deleteFile(Long prdId);
}
