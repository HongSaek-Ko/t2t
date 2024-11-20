package org.t2t.mem.dto;

import lombok.Getter;

@Getter
public enum Trade {
    TRD01("TRD01", "충전"), TRD02("TRD02", "충전");

    private String key;
    private String desc;

    private Trade(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
