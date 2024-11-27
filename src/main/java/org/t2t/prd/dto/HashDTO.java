package org.t2t.prd.dto;

import java.time.LocalDateTime;

public class HashDTO {
    private Long tagId; // pk, ai는 아님...?
    private String tagNm; // n/n, 태그명
    private char enabled; // 사용 가능 여부(Y/N), default Y
    private LocalDateTime regDt;
    private LocalDateTime lastDt;


}
