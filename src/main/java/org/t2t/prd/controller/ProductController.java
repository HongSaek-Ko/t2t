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
