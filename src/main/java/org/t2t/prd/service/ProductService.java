package org.t2t.prd.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.t2t.prd.dto.GoodDTO;
import org.t2t.prd.repository.ProductMapper;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductMapper productMapper;
    public void updateGood(Long prdId, String usrId) {
        Map<String, Object> map = new HashMap<>();
        map.put("prdId", prdId);
        map.put("usrId", usrId);

        GoodDTO goodDTO = productMapper.findGoodById(map);
        if(goodDTO == null) {
            log.debug("없다 ");
            productMapper.insertGood(map);
        } else {
            log.debug("있다. ");
            productMapper.deleteGood(map);
        }
    }
}
