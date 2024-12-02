package org.t2t.prd.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.t2t.prd.dto.*;
import org.t2t.prd.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {
    private final ReplyService replyService;

    // 댓글 작성
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<ReplyDTO> addReply(@RequestBody ReplyDTO replyDTO) {
        log.info("add reply: {}", replyDTO);
        try {
            // 댓글 추가하고 저장된 댓글 DTO 객체 반환
            ReplyDTO savedReply = replyService.addReply(replyDTO);
            log.info("댓글 저장됐어용: {}", savedReply);
            return new ResponseEntity<>(savedReply, HttpStatus.CREATED); // 성공적인 응답 반환
        } catch (Exception e) {
            log.info("댓글 에러에용: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 실패 시...
        }
    }

    // 댓글 수정
    @PutMapping("/{rpyId}")
    public ReplyDTO updateReply(@PathVariable(name="rpyId") Long rpyId, @RequestBody ReplyDTO replyDTO) {
        replyDTO.setRpyId(rpyId);
        return replyService.updateReply(replyDTO);
    }

    // 댓글 삭제
    @DeleteMapping("/{rpyId}")
    public void deleteReply(@PathVariable(name="rpyId") Long rpyId) {
        replyService.deleteReply(rpyId);
    }

    // 댓글 목록 조회
    @GetMapping("/{prdId}")
    public List<ReplyDTO> getReplies(@PathVariable(name="prdId") Long prdId) {
        return replyService.getReplyByPrdId(prdId);
    }
}


