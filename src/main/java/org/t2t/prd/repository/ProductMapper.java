package org.t2t.prd.repository;
import org.apache.ibatis.annotations.Mapper;
import org.t2t.prd.dto.GoodDTO;
import org.t2t.prd.dto.ProductDTO;
import org.t2t.prd.dto.ProductFormDTO;

import java.util.Map;

@Mapper
public interface ProductMapper {
    void write(ProductDTO product);

    ProductDTO findByPrdId(Long prdId);

    void modify(ProductFormDTO product);

    GoodDTO findGoodById(Map<String, Object> map);

    void deleteGood(Map<String, Object> map);

    void insertGood(Map<String, Object> map);

    void deleteProduct(Long prdId);
}
