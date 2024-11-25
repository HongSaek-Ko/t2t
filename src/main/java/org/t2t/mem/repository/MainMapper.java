package org.t2t.mem.repository;

import com.sun.tools.javac.Main;
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
    MainDTO idPwCheck(@Param("usrId") String usrId, @Param("passwd") String passwd);

    // usrId 찾기
    MainDTO findMemberId(MainDTO mainDTO);

    // 비밀번호 찾기
    int pwdCheck(MainDTO mainDTO);

    // 임시 비밀번호로 DB update
    void pwdUpdate(MainDTO mainDTO);

    // Password 찾기


}
