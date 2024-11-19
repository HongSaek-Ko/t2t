package org.t2t.mem.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum MemberStatus {
    MEM01("MEM01","활동중"),
    MEM02("MEM02","휴먼상태"),
    MEM03("MEM03","활동정지"),
    MEM04("MEM04","탈퇴"),
    MEM05("MEM05","시도횟수 초과");
    
    private String desc;
    private String key;
    MemberStatus(String key,String desc){
        this.key = key;
        this.desc = desc;
    }
}
