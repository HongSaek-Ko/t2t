package org.t2t.mem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MainDTO {
    private String usrId;
    private String passwd;
    private String nm;
    private String email;
    private String roleId;
    private String memStat;
    private String intro;
//    private String profImg;
//    private String profOrgImg;
    private int logAtmCnt;
    private LocalDateTime chgPassDt;
    private String bankNm;
    private String bankAcnt;
    private String bankAcntOwr;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;

    // 프로필 파일 정보 by moon
    private ProfileDTO imageProfile;  // 이미지 1개 파일
}
