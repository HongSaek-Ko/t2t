package org.t2t.mem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t2t.mem.dto.*;
import org.t2t.mem.repository.MainMapper;
import org.t2t.mem.repository.ProfileMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final MainMapper mainMapper;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    private final MemberService memberService;

    @Transactional // 2개 이상 CUD 시 필수 작성; 오류 발생 시 작업 취소(롤백)
    // 회원 가입
    public void insertMember(MainFormDTO member) throws IOException, NoSuchAlgorithmException {
        // 실제 파일 저장처리 -> ProfileService
        member.setRoleId(RoleId.MEMBER.name());
        member.setMemStat(MemberStatus.MEM01.getKey());
        member.setLogAtmCnt(0);
        member.setChgPassDt(LocalDateTime.now());
        // Password 인코딩("SHA512")
        member.setPasswd(encode(member.getPasswd()));
        MainDTO mainDTO = member.toMainDTO();
        mainMapper.insertMember(mainDTO); // 저장 이후 id 생성
        memberService.insertMile(makeEmptyMile(mainDTO));
        memberService.insertRank(makeEmptyRank(mainDTO));

        // DB에 member 주면서 저장해라~
        // MainFormDTO -> MainDTO로 변환해서 전달
        log.info("MainService - write - mainDTO : {}", mainDTO);

        ProfileDTO imgProfile = profileService.saveFile(member.getImageProfile());// 이미지 실제 파일 저장
        // DB에 게시글 정보 저장(+파일정보 포함) -> MainService
        log.info("POST /guest/signup - imgProfile : {}", imgProfile);

        // 파일 저장시 usrId 필요
        // 이미지 파일정보 있으면, DB에 저장
        if (imgProfile != null) {
            imgProfile.setUsrId(member.getUsrId());
            profileMapper.insertFile(imgProfile); // 저장
        }
    }

    private RankingDTO makeEmptyRank(MainDTO mainDTO) {
        RankingDTO rankingDTO = new RankingDTO();
        rankingDTO.setUsrId(mainDTO.getUsrId());
        rankingDTO.setScore(0L);
        return rankingDTO;
    }

    private MileDTO makeEmptyMile(MainDTO mainDTO) {
        MileDTO mileDTO = new MileDTO();
        mileDTO.setUsrId(mainDTO.getUsrId());
        mileDTO.setPoint(0L);
        return mileDTO;
    }

    public MainDTO findMemberId(MainDTO mainDTO) throws IOException {
        return  mainMapper.findMemberId(mainDTO);
    }

    public int pwdCheck(MainDTO mainDTO) throws NoSuchAlgorithmException {
        mainDTO.setPasswd(encode(mainDTO.getPasswd()));
        log.info("MainService - pwdCheck - mainDTO PW : {}", mainDTO.getPasswd());
        return mainMapper.pwdCheck(mainDTO);
    }

    //새로운 비밀번호로 저장
    public void pwdUpdate(MainDTO mainDTO) {
//        String newUpdatePwd = pwEncoder.encode(mainDTO.getPasswd());
//        mainDTO.setPwd(newUpdatePwd);
        log.info(mainDTO.getPasswd());
        mainMapper.pwdUpdate(mainDTO);
    }


    private String encode(String passwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        // digest() method is called
        // to calculate message digest of the input string
        // returned as array of byte
        byte[] messageDigest = md.digest(passwd.getBytes());
        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);
        // Convert message digest into hex value
        String hashtext = no.toString(16);
        // Add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}

