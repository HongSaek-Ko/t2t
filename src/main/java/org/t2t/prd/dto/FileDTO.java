package org.t2t.prd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private Long prdId;
    private String fileNm;
    private String fileOrgNm;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;

    public FileDTO(String fileNm, String fileOrgNm) {
        this.fileNm = fileNm;
        this.fileOrgNm = fileOrgNm;
    }

}
