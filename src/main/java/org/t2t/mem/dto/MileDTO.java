package org.t2t.mem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@ToString
@AllArgsConstructor
public class MileDTO {
    private String usrId;
    private Long point;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;
    private String option;
}
