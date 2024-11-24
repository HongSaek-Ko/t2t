package org.t2t.prd.repository;

import org.apache.ibatis.annotations.Mapper;
import org.t2t.prd.dto.Pager;
import org.t2t.prd.dto.ReplyDTO;

import java.util.List;

@Mapper
public interface ReplyMapper {
    void addReply(ReplyDTO replyDTO);

    void updateReply(ReplyDTO replyDTO);

    void deleteReply(Long rpyId);

    List<ReplyDTO> getReplyByPrd(Long rpyId);

//    int addReply(ReplyDTO replyDTO);
//
//    ReplyDTO readByRpyId(Long rpyId);
//
//    int updateReply(ReplyDTO replyDTO);
//
//    void deleteReply(Long rpyId);
//
//    List<ReplyDTO> readAll(Long prdId);
//
//    List<ReplyDTO> readAllWithPaging(Long bid, Pager pager);
//
//    int getReplyCount(Long prdId);
}
