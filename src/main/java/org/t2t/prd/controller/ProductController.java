package org.t2t.prd.controller;

import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.mem.dto.ProfileDTO;
import org.t2t.mem.repository.ProfileMapper;
import org.t2t.mem.service.MemberService;
import org.t2t.mem.service.ProfileService;
import org.t2t.prd.dto.*;
import org.t2t.prd.repository.ProductMapper;
import org.t2t.prd.service.FileService;
import org.t2t.prd.service.ProductService;
import org.t2t.prd.service.TagService;

import java.awt.print.Pageable;
import java.io.IOException;
import java.lang.reflect.Member;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;
    private final TagService tagService;
    private final ProductMapper productMapper;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private final MemberService memberService;

    @Value("${HTTP_SESSION_USER}")
    private String HTTP_SESSION_USER;


    // 상픔 등록 페이지 요청
    @GetMapping("/add")
    public String productAdd(@ModelAttribute("product") ProductFormDTO product) {
        log.info("상품 등록 페이지!");
        return "product/add";
    }

    // 상품 등록 처리 (게시글 정보 + 이미지 + 해시태그)
    @PostMapping("/add")
    public String productAddPost(@ModelAttribute("product") ProductFormDTO product, @RequestParam(name="tagId") String tagId) throws IOException{
        log.info("productDTO: {}", product);
        productService.write(product);

        String[] tags = tagId.split(",");
        List<PrdHashDTO> tagList = new ArrayList<>();
        for(String tag : tags) {
            tagList.add(new PrdHashDTO(tag, product.getPrdId()));
        }

        tagService.saveTags(tagList);
        log.info("상품 등록 완료!");
        return "redirect:/product/" + product.getPrdId();
    }

    // 상품 목록 페이지 todo: 임시로 list, 실제로는 index...?
    @GetMapping("/list")
    public String productList() {
        return "product/list";
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<List<ProductDTO>> moreProductList(Pager pager) {
        List<ProductDTO> productList = productService.getProductListWithPaging(pager);
//        model.addAttribute("productList", productList);
//        model.addAttribute("pageDTO", new PageDTO(pager, productService.getProductCount(pager)));
        return ResponseEntity.ok(productList);
    }

    // 상품 상세 페이지
    @GetMapping("/{prdId}")
    public String productDetail(@PathVariable("prdId") Long prdId, Model model, HttpSession session) throws MalformedURLException {
        log.info("상품 상세 페이지! prdId: {}", prdId);
        if(session.getAttribute(HTTP_SESSION_USER) != null) {
            MemberDTO loggedInMember = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
            model.addAttribute("clickgood", productService.clickgood(prdId, loggedInMember.getUsrId()));
        }
        // prdId로 prd 하나 가져오기
        ProductDTO product = productService.getProduct(prdId);
        model.addAttribute("product", product);
        model.addAttribute("goodCount", productService.goodCount(prdId));

        // Prd에서 usrId 가져오기
        String usrId = product.getUsrId();
        log.info("usrId : {}", usrId);

        // Prd에서 가져온 usrId로 member 정보 가져오기 + 모델 객체에 담기
        MemberDTO member = memberService.findByUserId(product.getUsrId());
        log.info("member: {}", member.toString());
        model.addAttribute("member", member);

        // Prd에서 가져온 usrId로 프로필 이미지 가져오기
        if(profileMapper.selectFileList(usrId) != null) {
            ProfileDTO profImg = profileMapper.selectFileList(usrId);
            log.info("profImg: {} ", profImg.toString());
        }

        return "product/detail";
    }

    // 이미지 요청
    @ResponseBody // 데이터 리턴
    @GetMapping("/image/{fileNm}") // PathVariable("") 지정해줘야 함 (매개변수 이름 인식 문제, -parameters 옵션 필요)
    public Resource getImages(@PathVariable("fileNm") String fileNm) throws MalformedURLException {
        log.info("GET /product/images - filename : {}", fileNm);
        return new UrlResource("file:" + fileService.getFullPath(fileNm));
    }

    @GetMapping("/{prdId}/modify")
    public String productModify(@PathVariable("prdId") Long prdId, Model model) {
        log.info("상품 수정 페이지! prdId: {}", prdId);
        ProductDTO product = productService.getProduct(prdId);
        model.addAttribute("product", product);
        return "product/modify";
    }

    @PostMapping("/{prdId}/modify")
    public String productModifyPost(@PathVariable("prdId") Long prdId, ProductFormDTO product, Model model) {
        log.info("상품 수정 처리!");
        log.info("prdId: {}", prdId);
        log.info("productDTO: {}", product);

        productService.modify(product);
        return "redirect:/product/{prdId}";
    }

    @PostMapping("/{prdId}/delete")
    public String productDelete(@PathVariable(name = "prdId") Long prdId) {
        productService.productDelete(prdId);
        log.info("제품 삭제?");
        return "redirect:/";
    }

    // 게시글에 해당하는 해시태그 조회
    @GetMapping("/product/tags/{prdId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> productHash(@PathVariable(name="prdId") Long prdId, Model model) {
        ProductDTO product = (ProductDTO) model.getAttribute("product"); // model에 저장된 ProductDTO 객체 가져오기
        Map<String, Object> map = new HashMap<>(); //
        map.put("prdHash", prdId); //
        return ResponseEntity.ok(map);
    }

    // '좋아요'
    @GetMapping("/detail/good/{prdId}")
    @ResponseBody // Http 응답; Http 요청을 객체로 변환 or 객체를 응답으로 변환 (응답 본문에 데이터를 담아 변환)
    public ResponseEntity<Map<String, Object>> updateGood(@PathVariable(name="prdId")Long prdId, HttpSession session) {
        MemberDTO user = (MemberDTO) session.getAttribute(HTTP_SESSION_USER); // HttpSession에 저장된 MemberDTO 객체(사용자 정보)를 가져옴;
        // H_S_U: 세션에서 MemberDTO를 가져오는 키값. 상술했듯, usrId 등 보유.
        productService.updateGood(prdId, user.getUsrId()); // MemberDTO 객체에서 usrId 추출.
        Map<String, Object> map = new HashMap<>(); // 문자열을 key, object를 value로 갖는 객체 map 생성.
        map.put("good", prdId); // 객체 map에 다음 값을 추가; key: 'good', value: prdId.
        return ResponseEntity.ok(map); // map은 {"good" : prdId} 형태로 응답 (응답 본문에 map, 상태코드는 200(OK)로 설정) → 서비스로!
    }

    @GetMapping("/goodCount/{prdId}")
    @ResponseBody
    public int getGoodCount(@PathVariable(name="prdId")Long prdId) {
        log.info("ajax post 요청! prdId: {}", prdId);
        return productService.goodCount(prdId);
    }



}
