package org.t2t.mem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.t2t.mem.dto.*;
import org.t2t.mem.repository.MemberMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

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
    //마일리지 충전/환전
    public void addMile(MileDTO mile) {
        memberMapper.updateMile(mile);
    }
    //회원정보 수정
    public void modifyMem(String usrId){
        memberMapper.updateMem(usrId);
    }
    //마일리지 전체 목록 불러오기
    public List<MileDTO> selectMileTotal(String usrId) {
        return memberMapper.selectMileTotal(usrId);
    }
    //회원 탈퇴
    public void deleteMem(String usrId){
        memberMapper.deleteMem(usrId);
    }


     }

