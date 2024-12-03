package org.t2t.mem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.t2t.mem.dto.*;
import org.t2t.mem.repository.MemberMapper;
import org.t2t.mem.repository.ProfileMapper;


import java.io.IOException;
import java.lang.reflect.Member;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberMapper memberMapper;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    //신고리스트
    public List<ComplaintDTO> findComplaintListByUsrId(String usrId) {
        return memberMapper.selectComplaintList(usrId);
    }
    //회원 한명 찾기
    public MemberDTO findByUserId(String usrId) {
        MemberDTO memberDTO = memberMapper.findByID(usrId);
        MileDTO mileDTO = memberMapper.selectMileByUsrId(memberDTO);
        RankingDTO rankingDTO = memberMapper.selectRankByUsrId(memberDTO);
        memberDTO.setMile(mileDTO);
        memberDTO.setRank(rankingDTO);
        return memberDTO;
    }
    //마일리지 충전/환전
    public void updownMile(MileDTO mile) {
        memberMapper.updateMile(mile);
    }


    //회원정보 수정
    public void modifyMem(MainFormDTO mainFormDTO) throws IOException {
        // MainFormDTO -> MemberDTO로 변환해서 전달
        MemberDTO memberDTO = mainFormDTO.toMemberDTO();
        // DB에 member 주면서 저장해라~
        memberMapper.updateMem(memberDTO);
        log.info("memberDTO : {}", memberDTO.toString());
        // 실제 파일 저장처리 -> ProfileService
        ProfileDTO imgProfile = profileService.saveFile((MultipartFile) mainFormDTO.getImageProfile());// 이미지 실제 파일 저장

        // 파일 저장시 usrId 필요
        // 이미지 파일정보 있으면, DB에 저장
        if (imgProfile != null) {
            imgProfile.setUsrId(memberDTO.getUsrId());
            log.info("imgProfile : {}", imgProfile.toString());
            profileMapper.updateProfile(imgProfile); // 저장
        }
        return;
    }


    //회원 탈퇴 시 id,pw 일치여부 확인
    public MemberDTO idPwCheck(@Param("usrId") String usrId, @Param("passwd") String passwd){
        return memberMapper.idPwChecking(usrId,passwd);
    }
    //마일리지 전체 목록 불러오기
    public List<MileDTO> selectMileTotal(String usrId) {
        return memberMapper.selectMileTotal(usrId);
    }
    //나의 구매목록 불러오기(판매상태에 따른)
    public void selectOrderList(String usrId){
        memberMapper.selectOrderList(usrId);
    }
    //나의 거래목록 불러오기(판매상태에 따른)
    public void selectTradeList(String usrId){
        memberMapper.selectTradeList(usrId);
    }
    //나의 마일리지 정보 마일리지 테이블에 넣기
    public void insertMile(MileDTO mileDTO){
        memberMapper.insertMile(mileDTO);
    }
    //나의 등급 정보 랭킹 테이블에 넣기
    public void insertRank(RankingDTO Rank){
        memberMapper.insertRank(Rank);
    }

    //비밀번호 변경
    public void changePassword(MemberDTO memberDTO) throws NoSuchAlgorithmException {
        memberDTO.setPasswd(encode(memberDTO.getPasswd()));
        memberMapper.updatePassword(memberDTO);
    }
    //변경 후 암호화처리
    // 비밀번호를 "SHA-512"방식을 이용하여 hashtext로 인코딩하는 메서드
    private String encode(String passwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
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
    //나의 현재 거래내역 보기
    public void selectCurTradeList(String usrId) {
    }
    //나의 과거 거래내역 보기
    public void selectPastTradeList(String usrId) {
    }
}

