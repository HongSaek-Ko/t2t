package org.t2t.mem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.t2t.mem.dto.*;
import org.t2t.mem.repository.MainMapper;
import org.t2t.mem.repository.ProfileMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final MainMapper mainMapper;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;


    public void insertMember(MainFormDTO member) throws IOException {
        // 실제 파일 저장처리 -> ProfileService
        member.setRoleId(RoleId.MEMBER.name());
        member.setMemStat(MemberStatus.MEM01.getKey());
        member.setLogAtmCnt(0);
        member.setChgPassDt(LocalDateTime.now());
        ProfileDTO imgProfile = profileService.saveFile(member.getImageProfile());// 이미지 실제 파일 저장

        // DB에 게시글 정보 저장(+파일정보 포함) -> MainService
        log.info("POST /guest/signup - imgProfile : {}", imgProfile);
        // DB에 member 주면서 저장해라~
        // MainFormDTO -> MainDTO로 변환해서 전달
        MainDTO mainDTO = member.toMainDTO();
        mainMapper.insertMember(mainDTO); // 저장 이후 id 생성
        log.info("MainService - write - mainDTO : {}", mainDTO);

        // 파일 저장시 usrId 필요
        // 이미지 파일정보 있으면, DB에 저장
        if (imgProfile != null) {
            imgProfile.setUsrId(member.getUsrId());
            profileMapper.insertFile(imgProfile); // 저장
        }
    }

    public MainDTO findMemberId(MainDTO mainDTO) throws IOException {
        return  mainMapper.findMemberId(mainDTO);
    }

    public int pwdCheck(MainDTO mainDTO) {
        return mainMapper.pwdCheck(mainDTO);
    }

    //새로운 비밀번호로 저장
    public void pwdUpdate(MainDTO mainDTO) {
//        String newUpdatePwd = pwEncoder.encode(mainDTO.getPasswd());
//        mainDTO.setPwd(newUpdatePwd);
        log.info(mainDTO.getPasswd());
        mainMapper.pwdUpdate(mainDTO);
    }

}

