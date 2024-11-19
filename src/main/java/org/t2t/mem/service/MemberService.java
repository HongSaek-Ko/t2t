package org.t2t.mem.service;

import org.springframework.stereotype.Service;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.mem.dto.MemberStatus;
import org.t2t.mem.dto.RoleId;

import java.time.LocalDateTime;

@Service
public class MemberService {
    public MemberDTO findByUserId(String usrId) {
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
