package org.t2t.mem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ComplaintDTO {
    private int cmpId;
    private String usrId;
    private int prdId;
    private String cmpCateCd;
    private String cmpStat;
    private String admId;
    private String cRstCd;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;

}
