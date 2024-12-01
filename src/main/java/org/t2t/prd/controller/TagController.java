package org.t2t.prd.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.t2t.prd.dto.HashDTO;
import org.t2t.prd.dto.PrdHashDTO;
import org.t2t.prd.service.ProductService;
import org.t2t.prd.service.TagService;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/tags")
@Controller
@RequiredArgsConstructor
@Slf4j
public class TagController {
    private final TagService tagService;
    private final ProductService productService;

    @GetMapping("/{tagId}")
    public ResponseEntity<List<HashDTO>> getTags(@PathVariable(name="tagId") String tagId) {
        log.info("getTags: {}", tagId);
        List<HashDTO> hashDTO = tagService.getTagsByTagId(tagId);
        return new ResponseEntity<>(hashDTO, HttpStatus.OK);
    }


    // 게시글에 해시태그 등록 (ajax 요청)
    @PostMapping("/addPrdHash")
    @ResponseBody
    public ResponseEntity<List<PrdHashDTO>> addPrdHash(@RequestBody List<PrdHashDTO> hashDTOList) {
        tagService.saveTags(hashDTOList);
        return ResponseEntity.ok(hashDTOList);
    }

    @GetMapping("/prdHash/{prdId}")
    @ResponseBody // 이거 추가 안하면 return값을 view 경로로 인식해서, 해당 html 파일을 만들지 않으면 에러 발생
    public List<PrdHashDTO> getPrdHashByPrdId(@PathVariable(name="prdId") Long prdId) {
        return tagService.getPrdHashByPrdId(prdId);
    }


}
