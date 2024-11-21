package org.t2t.mem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MainFormDTO {
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

    // 넘어오는 파일 정보 담아줄 변수
    private MultipartFile imageProfile;  // 폼에서 넘어오는 이미지

    // MemberFormDTO -> MemberDTO 변환해주는 메서드
    public MainDTO toMainDTO() {
        MainDTO mainDTO = new MainDTO();

        mainDTO.setUsrId(this.usrId);
        mainDTO.setPasswd(this.passwd);
        mainDTO.setNm(this.nm);
        mainDTO.setEmail(this.email);
        mainDTO.setRoleId(this.roleId);
        mainDTO.setMemStat(this.memStat);
        mainDTO.setIntro(this.intro);
        mainDTO.setLogAtmCnt(this.logAtmCnt);
        mainDTO.setChgPassDt(this.chgPassDt);
        mainDTO.setBankNm(this.bankNm);
        mainDTO.setBankAcnt(this.bankAcnt);
        mainDTO.setBankAcntOwr(this.bankAcntOwr);
        mainDTO.setRegDt(this.regDt);
        mainDTO.setLastDt(this.lastDt);

        return mainDTO;
    }
}