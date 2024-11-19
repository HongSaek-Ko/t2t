package org.t2t.mem.service;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class MileDTO {
    private String usrId;
    private Long point;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;
}
