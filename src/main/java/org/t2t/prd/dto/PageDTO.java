package org.t2t.prd.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 화면에 pagination 에 필요한 정보들 계산해서 담기
@Data
public class PageDTO {

    private final int PAGENUM_SIZE = 10;  // 페이지번호 10개씩 보여주자

    private int startPage;   // 페이지 시작번호
    private int endPage;     // 페이지 끝번호
    private boolean prev, next; // prev,next 활성화 여부

    private Pager pager; // page,size
    private int total;   // 게시글 전체 개수

    private List<Integer> pageNumbers; // 화면에 뿌려줘야할 페이지번호값 리스트에 미리 담기

    public PageDTO(Pager pager, int total) {
        this.pager = pager;      // page 11 size 10
        this.total = total;      // 132

        this.endPage = (int)(Math.ceil((double)pager.getPage() / PAGENUM_SIZE)) * PAGENUM_SIZE;
        this.startPage = this.endPage - (PAGENUM_SIZE - 1);
        int realEnd = (int)(Math.ceil((double)total / pager.getSize()));
        if(this.endPage > realEnd) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;


        //this.pageNumbers = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
        this.pageNumbers = new ArrayList<>();
        for(int i = startPage; i <= endPage; i++) {
            this.pageNumbers.add(i);
        }


    }
}
