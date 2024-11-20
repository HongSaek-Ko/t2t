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
    public List<CmpDTO> findComplaintListByUsrId(String usrId) {
        return memberMapper.selectComplaintList(usrId);
    }

    public MemberDTO findByUserId(String usrId) {
        return MemberDTO_.findByUsrId(usrId);
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

