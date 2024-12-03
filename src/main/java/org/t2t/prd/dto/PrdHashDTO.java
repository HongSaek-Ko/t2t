package org.t2t.prd.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrdHashDTO {
    private Long prdHashId; // pk, ai
    private String tagId; // fk
    private Long prdId; // fk
    private LocalDateTime regDt;
    private LocalDateTime lastDt;

    public PrdHashDTO(String tagId, Long prdId) {
        this.tagId = tagId;
        this.prdId = prdId;
    }
}
