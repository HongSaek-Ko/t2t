package org.t2t.prd.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest

class ProductMapperTest {
    @Autowired
    private ProductMapper productMapper;
    @Test
    public void insert() {
        Map<String, Object> map = new HashMap<>();
        map.put("prdId", 2L);
        map.put("usrId", "test2");

        productMapper.insertGood(map);
    }
}