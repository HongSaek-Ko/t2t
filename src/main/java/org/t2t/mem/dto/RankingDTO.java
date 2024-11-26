package org.t2t.mem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {
    private String usrId;
    private Long score;
    private LocalDateTime lastActDt;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;

}
