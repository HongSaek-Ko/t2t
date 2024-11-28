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

    // 회원 탈퇴(상태값만 MEM04로 변경)
    void deleteMember(String usrId);
//    // 회원 탈퇴(Profile 테이블에서 관련 레코드(행) 삭제)
//    void deleteMemberFromProfile(String usrId);
//    // 회원 탈퇴(Miles 테이블에서 관련 레코드(행) 삭제)
//    void deleteMemberFromMiles(String usrId);
//    // 회원 탈퇴(Ranking 테이블에서 관련 레코드(행) 삭제)
//    void deleteMemberFromRanking(String usrId);
}
