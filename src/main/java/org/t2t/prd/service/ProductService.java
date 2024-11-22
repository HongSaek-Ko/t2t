package org.t2t.prd.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t2t.prd.dto.FileDTO;
import org.t2t.prd.dto.GoodDTO;
import org.t2t.prd.dto.ProductDTO;
import org.t2t.prd.dto.ProductFormDTO;
import org.t2t.prd.repository.FileMapper;
import org.t2t.prd.repository.ProductMapper;

import java.io.File;
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

    public void write(ProductFormDTO product) throws IOException {
        FileDTO imgFile = fileService.uploadFile(product.getImgFile());
        log.info("게시글 등록 - 이미지 파일: {}: ", imgFile);

        ProductDTO productDTO = product.toProductDTO();
        productMapper.write(productDTO);// 실행이후, productDTO 의 prdId값이 체워져있다
        log.info("ProductService/write: productDTO: {}", productDTO);
        product.setPrdId(productDTO.getPrdId());

        imgFile.setPrdId(productDTO.getPrdId());
        fileMapper.insertFile(imgFile);


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


    public ProductDTO getProduct(Long prdId) {
        // prdId에 해당하는 글 가져오기
        ProductDTO findProduct = productMapper.findByPrdId(prdId);
        log.info("findProduct: {}", findProduct);

        // prdId에 해당하는 이미지 가져오기
        FileDTO imgFile = fileMapper.selectFile(prdId);
        findProduct.setImgFile(imgFile);

        return findProduct;
    }

    public void modify(ProductFormDTO product) {
        // 수정시간 표기...
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String datetime = "-";
        datetime = dateFormat.format(now.getTime());

        // 수정 시간 메서드
        product.setCont(product.getCont() + "\r\n 수정시간: " + datetime);

        // 수정 메서드
        productMapper.modify(product);
    }

}
