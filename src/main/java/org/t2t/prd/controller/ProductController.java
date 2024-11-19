package org.t2t.prd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/prd")
@Slf4j
public class ProductController {

    @GetMapping("/add")
    public String productAdd() {
        log.info("상품 등록 페이지!");
        return "product/add";
    }

    @GetMapping("/detail")
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
}
