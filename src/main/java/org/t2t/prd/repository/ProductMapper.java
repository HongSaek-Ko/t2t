package org.t2t.prd.repository;
import org.apache.ibatis.annotations.Mapper;
import org.t2t.prd.dto.GoodDTO;
import java.util.Map;

@Mapper
public interface ProductMapper {
    GoodDTO findGoodById(Map<String, Object> map);

    void deleteGood(Map<String, Object> map);

    void insertGood(Map<String, Object> map);
}
