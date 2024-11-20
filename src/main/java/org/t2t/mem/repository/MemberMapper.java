package org.t2t.mem.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.mem.dto.MileDTO;

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


}
