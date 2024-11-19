package org.t2t.mem.service;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@Builder
@ToString
public class RankingDTO {
    private String usrId;
    private Long score;
    private LocalDateTime regDt;
    private LocalDateTime lastActDt;
    private LocalDateTime lastDt;
}
