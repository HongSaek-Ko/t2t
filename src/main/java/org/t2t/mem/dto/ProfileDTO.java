package org.t2t.mem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProfileDTO {
    private int fId;
    private String usrId;
    private String profImg;
    private String profOrgImg;
    private String filetype;

    public ProfileDTO(String profImg, String profOrgImg, String filetype) {
        this.profImg = profImg;
        this.profOrgImg = profOrgImg;
        this.filetype = filetype;
    }
}
