package org.t2t.prd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.t2t.prd.dto.Pager;
import org.t2t.prd.dto.ReplyDTO;
import org.t2t.prd.dto.ReplyResponseDTO;
import org.t2t.prd.repository.ReplyMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyMapper replyMapper;

//    public int save(ReplyDTO replyDTO) {
//        return replyMapper.addReply(replyDTO);
//    }
//
//    public ReplyDTO getReply(Long rpyId) {
//        return replyMapper.readByRpyId(rpyId);
//    }
//
//    public int updateReply(ReplyDTO replyDTO) {
//        return replyMapper.updateReply(replyDTO);
//    }
//
//    public void delete(Long rpyId) {
//        replyMapper.deleteReply(rpyId);
//    }
//
//    public List<ReplyDTO> getAllReply(Long prdId) {
//        return replyMapper.readAll(prdId);
//    }
//
//    public ReplyResponseDTO getReplyListWithPaging(Long prdId, Pager pager) {
//        // new ReplyResponseDTO(댓글목록+paging, 댓글의 전체개수);
//        return new ReplyResponseDTO(replyMapper.readAllWithPaging(prdId, pager),
//                replyMapper.getReplyCount(prdId));
//    }

    // 댓글 추가
    public ReplyDTO addReply(ReplyDTO replyDTO) {
        replyMapper.addReply(replyDTO);
        return replyDTO; // 성공적으로 추가된 댓글 반환
    }

    // 댓글 수정
    public ReplyDTO updateReply(ReplyDTO replyDTO) {
        replyMapper.updateReply(replyDTO);
        return replyDTO; // 성공적으로 수정된 댓글 반환
    }

    // 댓글 삭제
    public void deleteReply(Long rpyId) {
        replyMapper.deleteReply(rpyId);
    }

    // 게시글에 달린 댓글 목록 조회
    public List<ReplyDTO> getReplyByPrdId(Long prdId) {
        return replyMapper.getReplyByPrd(prdId);
    }

}
