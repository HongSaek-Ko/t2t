package org.t2t.prd.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Slf4j
public class ProductFormDTO {
    private Long prdId;
    private String usrId;
    private String title;
    private String cont;
    private Long trgtSalQty;
    private Long curSalQty;
    private char islimit;
    private String salStat;
    private String abtReas;
    private String cate;
    private Long price;

    // 넘어오는 파일 정보 담을 변수
    private MultipartFile imgFile;

    public ProductDTO toProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrdId(this.prdId);
        productDTO.setUsrId(this.usrId);
        productDTO.setTitle(this.title);
        productDTO.setCont(this.cont);
        productDTO.setTrgtSalQty(this.trgtSalQty);
        productDTO.setCurSalQty(this.curSalQty);
        productDTO.setIslimit(this.islimit);
        productDTO.setSalStat(this.salStat);
        productDTO.setAbtReas(this.abtReas);
        productDTO.setCate(this.cate);
        productDTO.setPrice(this.price);
        return productDTO;
    }
}
