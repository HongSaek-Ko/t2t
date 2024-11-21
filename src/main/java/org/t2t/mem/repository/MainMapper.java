package org.t2t.mem.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.t2t.mem.dto.MainDTO;
import org.t2t.mem.dto.MemberDTO;

@Mapper
public interface MainMapper {
    // 회원 가입
    void insertMember(MainDTO member);

    // id로 회원 1명 조회
    MainDTO selectOne(String usrId);

    // id, pw 일치 확인 (login)
    MainDTO idPwCheck(@Param("id") String id, @Param("pw") String pw);

}
