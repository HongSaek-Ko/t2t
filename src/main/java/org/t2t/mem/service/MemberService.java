package org.t2t.mem.service;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.t2t.mem.dto.*;
import org.t2t.mem.repository.MemberMapper;
import org.t2t.prd.dto.ProductDTO;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    //신고리스트
    public List<ComplaintDTO> findComplaintListByUsrId(String usrId) {
        return memberMapper.selectComplaintList(usrId);
    }
    //회원 한명 찾기
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

    //회원 탈퇴
    public void deleteMember(String usrId){
        memberMapper.deleteMem(usrId);
    }

    //회원 탈퇴 시 id,pw 일치여부 확인
    public MemberDTO idPwCheck(@Param("usrId") String usrId, @Param("passwd") String passwd){
       return memberMapper.idPwChecking(usrId,passwd);
    }

    //마일리지 전체 목록 불러오기
    public List<MileDTO> selectMileTotal(String usrId) {
        return memberMapper.selectMileTotal(usrId);
    }
    //회원 탈퇴
    public void deleteMem(String usrId){
        memberMapper.deleteMem(usrId);
    }

    //나의 거래목록 불러오기(판매상태에 따른)
    public List<ProductDTO> selectTradeList(String usrId){
        return memberMapper.selectTradeList(usrId);
    }
}

