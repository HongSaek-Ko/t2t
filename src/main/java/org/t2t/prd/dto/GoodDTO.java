package org.t2t.prd.dto;

import lombok.*;

import java.time.LocalDateTime;
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodDTO {
    private Long goodId;
    private Long prdId;
    private String usrId;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;
}
