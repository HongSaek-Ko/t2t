package org.t2t.prd.repository;
import org.apache.ibatis.annotations.Mapper;
import org.t2t.prd.dto.GoodDTO;
import org.t2t.prd.dto.Pager;
import org.t2t.prd.dto.ProductDTO;
import org.t2t.prd.dto.ProductFormDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    void write(ProductDTO product);

    ProductDTO findByPrdId(Long prdId);

    void modify(ProductFormDTO product);

    GoodDTO findGoodById(Map<String, Object> map);

    void deleteGood(Map<String, Object> map);

    void insertGood(Map<String, Object> map);

    int goodCount(Long prdId);

    void deleteProduct(Long prdId);

    // 게시글 조회수 증가
    void updateProductHit(Long prdId);

//    // 게시글 전체 가져오기 (= 목록 조회)
//    List<ProductDTO> getProductList();

    // 게시글 목록 + 페이징
    List<ProductDTO> getPrdListWithPaging(Pager pager);

    // 게시글 전체 개수
    Long countAllProduct(Pager pager);
}
