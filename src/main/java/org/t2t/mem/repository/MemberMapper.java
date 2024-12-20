package org.t2t.mem.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.mem.dto.*;
import org.t2t.prd.dto.OrderDTO;
import org.t2t.prd.dto.ProductDTO;

import java.lang.reflect.Member;
import java.util.List;

@Mapper
public interface MemberMapper {
    //int, void : DB에서 작업하고 끝, update, delete, insert DB에서 작업하면 끝
    //return : 뭔가 다시 값을 되돌려줄 때, select 등 DB에서 작업한 후 화면에 뿌려줌

    //포인트 충전 / 환전
    void updateMile(MileDTO mile);
    //마일리지 목록 불러오기
    List<MileDTO> selectMileTotal(String usrId);
    //회원정보 수정
    void updateMem(MainFormDTO mainFormDTO);
    //나의 신고목록 가져오기
    List<ComplaintDTO> selectComplaintList(String usrId);
    //나의 구매(order)목록 가져오기
    List<OrderDTO> selectOrderList(String usrId);
    //나의 거래목록 가져오기
    void selectTradeList(String usrId);
    //id, pw 일치여부 확인
    MemberDTO idPwChecking(String usrId, String passwd);
    //회원정보 한명 가져오기
    MemberDTO findByID(String usrId);
    //회원 1명의 마일리지정보 가져오기
    MileDTO selectMileByUsrId(MemberDTO memberDTO);
    //회원 1명의 랭킹정보 가져오기
    RankingDTO selectRankByUsrId(MemberDTO memberDTO);
    //마일리지 테이블 추가
    void insertMile(MileDTO mileDTO);
    //랭킹 테이블 추가
    void insertRank(RankingDTO rank);
    //비밀번호 변경
    void updatePassword(MemberDTO memberDTO);
    //회원 전체 수 조회
    void countMember(String usrId);
}
