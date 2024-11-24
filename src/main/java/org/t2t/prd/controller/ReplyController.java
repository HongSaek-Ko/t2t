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
import org.t2t.prd.dto.Pager;
import org.t2t.prd.dto.ReplyDTO;
import org.t2t.prd.dto.ReplyResponseDTO;
import org.t2t.prd.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {


    private final ReplyService replyService;

    // 댓글 작성
    @PostMapping("/add")
    public ResponseEntity<ReplyDTO> addReply(@RequestBody ReplyDTO replyDTO) {
        try {
            // 댓글을 추가하고 저장된 댓글 DTO 객체 반환
            ReplyDTO savedReply = replyService.addReply(replyDTO);
            return new ResponseEntity<>(savedReply, HttpStatus.CREATED); // 성공적인 응답 반환
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 실패 시
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


//// 댓글 등록 요청 : json 으로 댓글 데이터 전송 -> ReplyDTO로 변환해서 받기
////  consumes : 사용자가 요청시 보내는 데이터의 Content-Type과 일치하도록 제약 걸어주는 것.
////  produces : 서버에서 생산하여 응답해주는 데이터의 미디어타입을 지정, 브라우저에서 요청할때 지정한 accept 헤더정보와 일치하게
//@PostMapping(value = "/add", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
//public ResponseEntity<String> add(@RequestBody ReplyDTO replyDTO) {
//    log.info("POST /replies/add - replyDTO: {}", replyDTO);
//    int savedCount = replyService.save(replyDTO); // 저장이 잘되면, 추가된 레코드 갯수인 1을 리턴.
//    log.info("POST /replies/add - savedCount: {}", savedCount);
//    return savedCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
//            : new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
//}
//    // 댓글 1개 조회 요청
//    @GetMapping("/{rpyId}")
//    public ResponseEntity<ReplyDTO> get(@PathVariable Long rpyId) {
//        ReplyDTO findReply = replyService.getReply(rpyId);
//        return new ResponseEntity<>(findReply, HttpStatus.OK);
//    }
//    // 댓글 수정 요청 : 수정요청시, 데이터는 JSON으로 올것임
//    //@RequestMapping(value = "/{rid}", method = {RequestMethod.PUT, RequestMethod.PATCH})
//    @PatchMapping(value = "/{rpyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> update(@RequestBody ReplyDTO replyDTO, @PathVariable Long rpyId) {
//        log.info("PATCH /replies/update - rpyId: {}", rpyId);
//        log.info("PATCH /replies/update - replyDTO: {}", replyDTO);
//        // JSON에 rid가 포함안되어있으면 PathVariable에서 꺼낸 rid를 replyDTO에 추가하고 update 실행
//        replyDTO.setRpyId(rpyId); // rid, reply 새댓글
//        int updatedCount = replyService.updateReply(replyDTO);
//
//        return updatedCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
//                : new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    // 댓글 삭제 요청
//    @DeleteMapping("/{rpyId}")
//    public ResponseEntity<String> delete(@PathVariable Long rpyId) {
//        log.info("DELETE /replies/delete - rpyId: {}", rpyId);
//        replyService.delete(rpyId);
//        return new ResponseEntity<>("success", HttpStatus.OK);
//    }
//
//    // 댓글 목록 조회 요청
//    @GetMapping("/list/{prdId}/{page}")
//    public ResponseEntity<ReplyResponseDTO> list(@PathVariable("prdId") Long prdId,
//                                                 @PathVariable("page") int page) {
//        log.info("GET /replies/list - bid: {}", prdId);
//
//        //List<ReplyDTO> replyList = replyService.getReplyList(bid);
//        ReplyResponseDTO result = replyService.getReplyListWithPaging(prdId, new Pager(page, 10));
//        // result.list = 댓글목록, result.replyCount = 댓글 전체 개수
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}


