package org.t2t.prd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReplyResponseDTO {
    private List<ReplyDTO> list;
    private int replyCount;
}
