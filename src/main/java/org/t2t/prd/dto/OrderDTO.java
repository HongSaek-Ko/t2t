package org.t2t.prd.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private int ordId;
    private String usrId;
    private int prdId;
    private LocalDateTime ordDt;
    // PRD 내용
    private String title;
    private String cont;
    private Long price;
    private String fileNm;
    private String fileOrgNm;
}