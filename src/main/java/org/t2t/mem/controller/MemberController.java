package org.t2t.mem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.mem.dto.Trade;
import org.t2t.mem.service.MemberService;
import org.t2t.mem.dto.MileDTO;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j

public class MemberController {
    private final MemberService memberService;

    @Value("${HTTP_SESSION_USER}")
    private String HTTP_SESSION_USER;

    @GetMapping("/mypage")
    public String getMypage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        MemberDTO findUser = memberService.findByUserId("test1");
        model.addAttribute("myuser", findUser);
        return "member/mypage";
    }

    //포인트 충전/ 환전
    @PostMapping("/mypage/charge")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addPoint(@RequestParam(name="point") Long point, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);

        memberService.addMile(MileDTO.builder()
                        .usrId(user.getUsrId())
                        .point(point)
                        .option(Trade.TRD01.getKey())
                        .build());

        MileDTO totalMile = memberService.selectMileTotal(user.getUsrId()).get(0);
        // 로그인 성공 응답
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("mile", totalMile.getPoint());

        return ResponseEntity.ok(map);
    };



    //마이페이지 수정
    @GetMapping("/s")
    public String mypagemodify(@PathVariable("usrID") String usrId){
        memberService.modifyMem(usrId);
        return "/member/mypage";
    }

    //회원 탈퇴
    @GetMapping("/mypagedelete")
    public String deletemember(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        memberService.deleteMem(user.getUsrId());
        return "/index";
    }

    //나의 판매/ 구매목록 리스트 보기
    @GetMapping("/mypageList")
    public String mypagelist(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        MemberDTO findUser = memberService.findByUserId("test1");
        return "/member/mypageList";
    }


    //나의 신고내역
    @GetMapping("/mycomplaint")
    public String myComplaint(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        MemberDTO findUser = memberService.findByUserId("test1");
        return "/member/mycomplaint";
    }
}
