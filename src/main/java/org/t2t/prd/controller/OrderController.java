package org.t2t.prd.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.prd.dto.OrderDTO;
import org.t2t.prd.service.OrderService;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Value("${HTTP_SESSION_USER}")
    private String HTTP_SESSION_USER;

    @ResponseBody
    @GetMapping("/product/{prdId}/purchase")
    public ResponseEntity<Map<String, Object>> purchase(@PathVariable("prdId") Long prdId, HttpSession session) {
        MemberDTO user = (MemberDTO) session.getAttribute(HTTP_SESSION_USER);
        log.info("prdId: {}", prdId);
        orderService.purchase(prdId, user.getUsrId());
        Map<String, Object> map = new HashMap<>();
        map.put("order", prdId);
        return ResponseEntity.ok(map);
    }
}
