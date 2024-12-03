package org.t2t.prd.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t2t.prd.dto.*;
import org.t2t.prd.repository.FileMapper;
import org.t2t.prd.repository.ProductMapper;
import org.t2t.prd.repository.TagMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    private final ProductMapper productMapper;
    private final FileMapper fileMapper;
    private final FileService fileService;
    private final TagService tagService;
    private final TagMapper tagMapper;

    public void write(ProductFormDTO product) throws IOException {

        // ProductFormDTO에 저장된 값 productDTO에 전달
        ProductDTO productDTO = product.toProductDTO();

        // 게시글 정보 저장
        productMapper.write(productDTO);// 실행이후, productDTO 의 prdId값이 채워져있음
        log.info("ProductService/write: productDTO: {}", productDTO);
        product.setPrdId(productDTO.getPrdId()); // formDTO의 prdId값을 일반prdId을 가져와서 설정

        // 파일 저장
        FileDTO imgFile = fileService.uploadFile(product.getImgFile());
        log.info("게시글 등록 - 이미지 파일: {}: ", imgFile);
        imgFile.setPrdId(productDTO.getPrdId()); // 이미지 prdId 값도...
        fileMapper.insertFile(imgFile); // File에 저장
    }


    public void updateGood(Long prdId, String usrId) {
        Map<String, Object> map = new HashMap<>();
        map.put("prdId", prdId); // k: prdId (컨트롤러에서 전달된 값), v: prdId
        map.put("usrId", usrId); // k: usrId (컨트롤러에서 전달된 값), v: usrId

        // prdMap.의 메서드 findGoodById에 매개변수 map을 준 리턴값을 goodDTO에 담을 거임.
        GoodDTO goodDTO = productMapper.findGoodById(map); // prdId, usrId를 가진 기록이 있다면 그것을 담은 GoodDTO가 반환될 것임...

        if(goodDTO == null) { // goodDTO가 null = 담긴 값이 없음 = " 를 가진 기록이 없음 = 좋아요(GoodDTO)에 담긴 정보가 없음 = 좋아요 누른 적 없음
            log.debug("없다 ");
            productMapper.insertGood(map); // 메서드 insertGood을 호출, 매개변수 map(prdId, usrId), 해당 리턴값 반환...
        } else { // 그 반대의 경우...
            log.debug("있다. ");
            productMapper.deleteGood(map); // deleteGood을 호출, 좋아요 기록 삭제.
        }
    }
    // 게시글 조회(상세보기)
    public ProductDTO getProduct(Long prdId) {
        // 조회수 올리기
        productMapper.updateProductHit(prdId);

        // prdId에 해당하는 글 가져오기
        ProductDTO findProduct = productMapper.findByPrdId(prdId);
        log.info("findProduct: {}", findProduct);

        // prdId에 해당하는 이미지 가져오기
        FileDTO imgFile = fileMapper.selectFile(prdId);
        findProduct.setImgFile(imgFile);

        // prdId에 해당하는 좋아요 수 가져오기
        productMapper.goodCount(prdId);

        return findProduct;
    }

    public void modify(ProductFormDTO product) {
        // 수정시간 표기... todo: 게시글 본문에 해당시키지 말아야 함...
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String datetime = "-";
        datetime = dateFormat.format(now.getTime());

        // 수정 시간 메서드
        product.setCont(product.getCont() + "\r\n 수정시간: " + datetime);

        // 수정 메서드
        productMapper.modify(product);
    }

    public void productDelete(Long prdId) {
        FileDTO file = fileMapper.selectFile(prdId);
        fileService.deleteFile(file.getFileNm());
        fileMapper.deleteFile(prdId);
        productMapper.deleteProduct(prdId);
    }

    public int goodCount(Long prdId) {
        return productMapper.goodCount(prdId);
    }

    // 게시글 목록 + 페이징 처리
    public List<ProductDTO> getProductListWithPaging(Pager pager) {
        List<ProductDTO> productList = productMapper.getPrdListWithPaging(pager);
        for(ProductDTO productDTO : productList) { // 게시글 갯수만큼 반복
            FileDTO productImage = fileMapper.selectFile(productDTO.getPrdId()); // 게시글 이미지 담아줄 객체 (이미지 정보 필요)
            int goodCount = productMapper.goodCount(productDTO.getPrdId()); // 게시글 좋아요 담아줄 객체 (숫자만 있으면 됨)
            productDTO.setImgFile(productImage);
            productDTO.setGoodCount(goodCount);
        }
        return productList;
    }

    // 글 전체 개수 조회
    public int getProductCount(Pager pager) {
        Long count = productMapper.countAllProduct(pager);
        return count.intValue(); // intvalue: 객체의 값을 정수로 변환
    }

    public boolean clickgood(Long prdId, String usrId) {
        Map<String, String> map = new HashMap<>();
        map.put("usrId", usrId);
        map.put("prdId",prdId.toString());
        return productMapper.clickgood(map) == 1 ? true : false;
    }
}
