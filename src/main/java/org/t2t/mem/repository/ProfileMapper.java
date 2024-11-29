package org.t2t.mem.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.mem.dto.ProfileDTO;

@Mapper
public interface ProfileMapper {
    // 파일 정보 저장
    void insertFile(ProfileDTO imgProfileDTO);

    void updateProfile(ProfileDTO profileDTO);
}
