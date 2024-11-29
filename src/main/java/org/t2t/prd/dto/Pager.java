package org.t2t.prd.dto;

import lombok.Data;

@Data
public class Pager {
    private int page;    // 현재 페이지 번호
    private int size;    // 한페이지당 보여줄 글의 개수
    //private int offset;  // DB에서 limit 구문에 사용할 값

    private String searchType; // 검색 타입   t, c, w, tw, tc, twc
    private String keyword;    // 검색 키워드

    public Pager(){
        this(1, 10); // page=1, 한페이지에 글 10개씩
    }
    public Pager(int page, int size) {
        this.page = page;
        this.size = size;
    }
    public int getOffset(){
        //this.offset = ;
        return (page - 1) * size;
    }

    public String[] getSearchTypeArr() {
        return searchType == null? new String[] {} : searchType.split("");
    }

}