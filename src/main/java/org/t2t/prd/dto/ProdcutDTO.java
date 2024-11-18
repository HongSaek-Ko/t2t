package org.t2t.prd.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProdcutDTO {
    private String prdId;
    private String usrId;
    private String title;
    private String cont;
    private String hit;
    private String trgtSalQty;
    private String curSalQty;
    private String islimit;
    private String salStat;
    private String abtReas;
    private String cate;
    private String price;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;
}
