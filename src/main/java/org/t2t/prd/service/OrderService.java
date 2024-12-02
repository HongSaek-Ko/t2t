package org.t2t.prd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t2t.prd.dto.OrderDTO;
import org.t2t.prd.repository.OrderMapper;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    public void purchase(Long prdId, String usrId) {
        Map<String, Object> map = new HashMap<>();
        map.put("prdId", prdId); // k: prdId. v: prdId
        map.put("usrId", usrId); // k: usrId, v: prdId
        orderMapper.purchase(map);
    }
}
