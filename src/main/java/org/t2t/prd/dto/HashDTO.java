package org.t2t.prd.dto;

import java.time.LocalDateTime;

public class HashDTO {
    private Long tagId; // pk, 사용자로부터 입력받음
    private String tagNm; // n/n, 태그명
    private char enabled; // 사용 가능 여부(Y/N), default Y, 셀렉트 시 enabled Y만 노출
    private LocalDateTime regDt;
    private LocalDateTime lastDt;

    private String hash;


}
