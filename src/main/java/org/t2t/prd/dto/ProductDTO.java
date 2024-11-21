package org.t2t.prd.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private Long prdId;
    private String usrId;
    private String title;
    private String cont;
    private Long hit;
    private Long trgtSalQty;
    private Long curSalQty;
    private char islimit;
    private String salStat;
    private String abtReas;
    private String cate;
    private Long price;
    private LocalDateTime regDt;
    private LocalDateTime lastDt;
}
