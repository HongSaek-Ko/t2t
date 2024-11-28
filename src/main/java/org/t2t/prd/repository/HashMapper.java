package org.t2t.prd.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.prd.dto.PrdHashDTO;

@Mapper
public interface HashMapper {

    void insertPrdHash(PrdHashDTO prdHash);

}
