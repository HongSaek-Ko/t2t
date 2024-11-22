package org.t2t.prd.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.t2t.prd.dto.FileDTO;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    // 파일 저장 경로
    @Value("D:/bhs/1stpj/") // application.yml파일에 작성된 정보 가져오기
    private String fileDir;

    // 파일 저장
    public FileDTO uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String fileOrgNm = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String ext = fileOrgNm.substring(fileOrgNm.lastIndexOf("."));
        String saveFileName = uuid + ext;
        log.info("orgFilename: {}", fileOrgNm);
        log.info("ext: {}", ext);
        log.info("uuid: {}", uuid);
        log.info("saveFilename: {}", saveFileName);

        file.transferTo(new File(fileDir + saveFileName));

        FileDTO fileDTO = new FileDTO(saveFileName, fileOrgNm);
        return fileDTO;
    }

    public String getFullPath(String fileNm){
        return fileDir + fileNm;
    }

}
