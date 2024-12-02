package org.t2t.prd.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDTO {
   private Long rpyId ; // 댓글 고유 번호
   private Long prtRpyId; // 원댓글의 고유 번호
   private String usrId; // 댓글 작성자
   private Long prdId; // (댓글이 달리는) 게시글 ID
   private String rpyCont; // 댓글 개수
   private LocalDateTime regDt;
   private LocalDateTime lastDt;
   }


