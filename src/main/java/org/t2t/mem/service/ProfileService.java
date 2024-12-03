package org.t2t.mem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.t2t.mem.dto.ProfileDTO;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class ProfileService {

    // 파일 저장 경로
    @Value("${file.profile.path}") // application.yml파일에 작성된 정보 가져오기
    private String fileDir;

    public ProfileDTO saveFile(MultipartFile file) throws IOException {
        // * 파일 없으면 메서드 강제 종료 null 리턴
        if(file == null || file.isEmpty()) {
            return null;
        }
        // * 파일 한개 저장
        // - 파일 타입 꺼내기
        String filetype = file.getContentType();
        log.info("contentType: {}", filetype);

        // - 저장할 파일명 준비
        String profOrgImg = file.getOriginalFilename(); // ex) mickey.png
        String uuid = UUID.randomUUID().toString();
        String ext = profOrgImg.substring(profOrgImg.lastIndexOf(".")); // .png
        String profImg = uuid + ext; // 저장할 파일명
        log.info("profOrgImg: {}", profOrgImg);
        log.info("ext: {}", ext);
        log.info("uuid: {}", uuid);
        log.info("profImg: {}", profImg);

        // - 실제 파일 폴더에 저장 : 저장할 폴더경로 + 저장할 파일명 -> File 객체
        file.transferTo(new File(fileDir + profImg));

        // 기본 이미지 설정
        //  String defaultImage = "/upload/profile/profile1.jpg";




        // * 저장된 파일의 정보를 ImgFileDTO로 리턴
        ProfileDTO profileDTO = new ProfileDTO(profImg, profOrgImg, filetype);
        return profileDTO;
    }

    //프로필 사진 변경
    public ProfileDTO updateFile(MultipartFile file) throws IOException {

        return null;
    }


    // 파일 전체 경로 리턴
    public String getFullPath(String filename) {
        return fileDir + filename;
    }




}
