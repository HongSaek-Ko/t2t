package org.t2t.prd.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.prd.dto.ProductDTO;
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

    // 상픔 등록 페이지 요청
    @GetMapping("/add")
    public String productAdd(@ModelAttribute("product") ProductDTO product, Model model) {
        log.info("상품 등록 페이지!");
        return "product/add";
    }

    // 상품 등록 처리
    @PostMapping("/add")
    public String productAddPost(ProductDTO productDTO) {

        log.info("상품 등록 완료!");
        log.info("productDTO: {}", productDTO);
        productService.write(productDTO);

        return "redirect:/product/" + productDTO.getPrdId();
    }

    @GetMapping("/{prdId}")
    public String productDetail(@PathVariable("prdId") Long prdId, Model model) {
        log.info("상품 상세 페이지! prdId: {}", prdId);
        ProductDTO product = productService.getProduct(prdId);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping("/{prdId}/modify")
    public String productModify(@PathVariable("prdId") Long prdId, Model model) {
        log.info("상품 수정 페이지! prdId: {}", prdId);
        ProductDTO product = productService.getProduct(prdId);
        model.addAttribute("product", product);
        return "product/modify";
    }

    @PostMapping("/{prdId}/modify")
    public String productModifyPost(@PathVariable("prdId") Long prdId, ProductDTO product, Model model) {
        log.info("상품 수정 처리!");
        log.info("prdId: {}", prdId);
        log.info("productDTO: {}", product);

        productService.modify(product);
        return "redirect:/product/{prdId}";
    }

    @GetMapping("/delete")
    public String productDelete() {
        log.info("제품 삭제?");
        return "product/delete";
    }

    // '좋아요'
    @GetMapping("/good/{prdId}")
    @ResponseBody // Http 응답; Http 요청을 객체로 변환 or 객체를 응답으로 변환 (응답 본문에 데이터를 담아 변환)
    public ResponseEntity<Map<String, Object>> updateGood(@PathVariable(name="prdId")Long prdId, HttpSession session) {
        MemberDTO user = (MemberDTO) session.getAttribute(HTTP_SESSION_USER); // HttpSession에 저장된 MemberDTO 객체(사용자 정보)를 가져옴;
        // H_S_U: 세션에서 MemberDTO를 가져오는 키값. 상술했듯, usrId 등 보유.
        productService.updateGood(prdId, user.getUsrId()); // MemberDTO 객체에서 usrId 추출.

        Map<String, Object> map = new HashMap<>(); // 문자열을 key, object를 value로 갖는 객체 map 생성.
        map.put("good", prdId); // 객체 map에 다음 값을 추가; key: 'good', value: prdId.
        return ResponseEntity.ok(map); // map은 {"good" : prdId} 형태로 응답 (응답 본문에 map, 상태코드는 200(OK)로 설정) → 서비스로!
    }
}
