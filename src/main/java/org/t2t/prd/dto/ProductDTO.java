package org.t2t.prd.dto;

import lombok.Data;
import org.t2t.mem.dto.MemberDTO;

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

    // 파일 정보
    private FileDTO imgFile;

    // 해시태그 정보
    private PrdHashDTO prdHash;

    // 댓글 정보
    private ReplyDTO replies;

    // 좋아요 정보
    private GoodDTO likes;

    // 좋아요 개수 정보
    private int goodCount;

    private MemberDTO uploadMemberInfo;

}
