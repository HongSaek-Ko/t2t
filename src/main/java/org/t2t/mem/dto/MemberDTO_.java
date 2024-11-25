package org.t2t.mem.dto;

import java.time.LocalDateTime;

//가짜 데이터
public class MemberDTO_ {
    public static MemberDTO findByUsrId (String usrId) {
        MileDTO mileDTO = MileDTO.builder()
                .usrId(usrId)
                .point(1000L)
                .regDt(LocalDateTime.now())
                .lastDt(LocalDateTime.now())
                .build();

        RankingDTO rankDTO = RankingDTO.builder()
                .usrId(usrId)
                .score(1000L)
                .lastActDt(LocalDateTime.now())
                .regDt(LocalDateTime.now())
                .lastDt(LocalDateTime.now())
                .build();

        MemberDTO dto = MemberDTO.builder()
                .usrId(usrId)
                .nm("짱나")
                .email("짱나@gmail.com")
                .roleId(RoleId.MEMBER.name())
                .memStat(MemberStatus.MEM01.getKey())
                .intro("나는 잘몰라요")
                .profImg("profile1.jpg")
                .profOrgImg("/upload/profile/profile1.jpg")
                .bankNm("카카오뱅크")
                .bankAcnt("333344445555")
                .bankAcntOwr("나짱")
                .regDt(LocalDateTime.now())
                .lastDt(LocalDateTime.now())
                .mile(mileDTO)
                .rank(rankDTO)
                .build();
        return dto;
    }
}
