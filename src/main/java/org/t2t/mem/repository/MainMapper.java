package org.t2t.mem.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.t2t.mem.dto.MainDTO;
import org.t2t.mem.dto.MemberDTO;

@Mapper
public interface MainMapper {
    // 회원 가입
    void insertMember(MainDTO member);

    MainDTO idPwCheck(@Param("id") String id, @Param("pw") String pw);

}
