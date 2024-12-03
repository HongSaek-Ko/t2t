package org.t2t.prd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t2t.prd.dto.HashDTO;
import org.t2t.prd.dto.PrdHashDTO;
import org.t2t.prd.dto.ProductFormDTO;
import org.t2t.prd.repository.TagMapper;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TagService {

    private final TagMapper tagMapper;

    public TagService(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    // tagId에 해당하는 해시태그 조회
    public List<HashDTO> getTagsByTagId(String tagId) {
        log.info("getTagsByTagId : {}", tagId);
        return tagMapper.getTagsByTagId(tagId);
    }

    public void saveTags(List<PrdHashDTO> tagId) {
        log.info("saveTags : {}", tagId);
        for (PrdHashDTO prdHashDTO : tagId) {
            log.info("saveTags? : {}", prdHashDTO);
            tagMapper.saveTags(prdHashDTO);
        }
    }

    public List<Map> getPrdHashByPrdId(Long prdId) {
        log.info("getPrdHashByPrdId : {}", prdId);
        return tagMapper.getPrdHashByPrdId(prdId);
    }
}
