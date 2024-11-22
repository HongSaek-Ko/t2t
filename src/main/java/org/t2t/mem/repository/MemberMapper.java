package org.t2t.mem.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.mem.dto.ComplaintDTO;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.mem.dto.MileDTO;
import org.t2t.prd.dto.OrderDTO;
import org.t2t.prd.dto.ProductDTO;

import java.util.List;

@Mapper
public interface MemberMapper {
    //포인트 충전 / 환전
    void updateMile(MileDTO mile);
    //마일리지 목록 불러오기
    List<MileDTO> selectMileTotal(String usrId);
    //회원정보 수정
    void updateMem(String usrId);
    //회원 탈퇴
    void deleteMem(String usrId);
    //나의 신고목록 가져오기
    List<ComplaintDTO> selectComplaintList(String usrId);
    //나의 구매(order)목록 가져오기
    void selectOrderList(String usrId);
    //나의 거래목록 가져오기
    void selectTradeList(String usrId);
    //id, pw 일치여부 확인
    MemberDTO idPwChecking(String usrId, String passwd);
}
