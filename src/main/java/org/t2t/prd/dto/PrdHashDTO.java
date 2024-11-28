package org.t2t.prd.dto;

import java.time.LocalDateTime;

public class PrdHashDTO {
    private Long prdHashId; // pk, ai
    private Long tagId; // fk
    private String tagNm; // fk, 태그 설명
    private LocalDateTime regDt;
    private LocalDateTime lastDt;
}
