package org.t2t.prd.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.prd.dto.HashDTO;

import java.util.List;

@Mapper
public interface TagMapper {
    List<HashDTO> getTagsByTagId(String tagId);

}
