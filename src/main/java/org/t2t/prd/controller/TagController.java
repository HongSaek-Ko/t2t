package org.t2t.prd.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.t2t.prd.dto.HashDTO;
import org.t2t.prd.service.TagService;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/tags")
@Controller
@RequiredArgsConstructor
@Slf4j
public class TagController {
    private final TagService tagService;

    @GetMapping("/{tagId}")
    public ResponseEntity<List<HashDTO>> getTags(@PathVariable(name="tagId") String tagId) {
        log.info("getTags: {}", tagId);
        List<HashDTO> hashDTO = tagService.getTagsByTagId(tagId);
        return new ResponseEntity<>(hashDTO, HttpStatus.OK);
    }

}
