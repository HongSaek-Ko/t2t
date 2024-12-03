package org.t2t.prd.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;
import org.t2t.prd.dto.HashDTO;
import org.t2t.prd.dto.PrdHashDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper {
    List<HashDTO> getTagsByTagId(String tagId);

    void saveTags(PrdHashDTO tagId);

    List<Map> getPrdHashByPrdId(Long prdId);

}
