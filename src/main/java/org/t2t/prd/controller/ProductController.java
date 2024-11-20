package org.t2t.prd.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.prd.service.ProductService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    @Value("${HTTP_SESSION_USER}")
    private String HTTP_SESSION_USER;

    @GetMapping("/add")
    public String productAdd() {
        log.info("상품 등록 페이지!");
        return "product/add";
    }

    @PostMapping("/add")
    public String productAddPost(){
        log.info("상품 등록 완료!");
        return "redirect:/product/detail"; // detail에서 {prdId} 로 바꿔야함
    }

    @GetMapping("/detail") // detail에서 {prdId} 로 바꿔야함
    public String productDetail() {
        log.info("상품 상세 페이지!");
        return "product/detail";
    }

    @GetMapping("/modify")
    public String productModify() {
        log.info("상품 수정 페이지!");
        return "product/modify";
    }

    @PostMapping("/modify")
    public String productModifyPost() {
        log.info("상품 수정 처리!");
        return "product/detail";
    }

    @GetMapping("/delete")
    public String productDelete() {
        log.info("제품 삭제?");
        return "product/delete";
    }

    @GetMapping("/good/{prdId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateGood(@PathVariable(name="prdId")Long prdId, HttpSession session) {
        MemberDTO user = (MemberDTO) session.getAttribute(HTTP_SESSION_USER);

        productService.updateGood(prdId, user.getUsrId());
        Map<String, Object> map = new HashMap<>();
        map.put("good", prdId);
        return ResponseEntity.ok(map);
    }
}
