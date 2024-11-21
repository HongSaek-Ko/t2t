package org.t2t.prd.dto;

import lombok.*;

import java.time.LocalDateTime;
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodDTO {
    private Long goodId; // '좋아요' id, pk.
    private Long prdId; // 상품 id. fk.
    private String usrId; // 유저 id. fk.
    private LocalDateTime regDt;
    private LocalDateTime lastDt;
}
