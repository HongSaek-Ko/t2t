package org.t2t.prd.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HashDTO {
    private Long tagId; // pk, 사용자로부터 입력받음
    private String tagNm; // n/n, 태그명
    private char enabled; // 사용 가능 여부(Y/N), default Y, 셀렉트 시 enabled Y만 노출
    private LocalDateTime regDt;
    private LocalDateTime lastDt;
    // 관리자가 관리 (사용자는 미리 입력된 해시태그 테이블에서 데이터 불러옴(자동완성)


}
