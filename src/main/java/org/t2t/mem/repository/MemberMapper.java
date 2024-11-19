package org.t2t.mem.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.mem.service.MileDTO;

@Mapper
public interface MemberMapper {
    //포인트 충전
    void addMile(Long point);
    //포인트 환전
    void minMile(Long point);
}
