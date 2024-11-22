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

import org.t2t.mem.dto.*;
import org.t2t.mem.service.MemberService;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j

public class MemberController {
    private final MemberService memberService;

    @Value("${HTTP_SESSION_USER}")
    private String HTTP_SESSION_USER;

    //내 프로필 보기
    @GetMapping("/mypage")
    public String getMypage(HttpSession session, Model model) {
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        MemberDTO findUser = memberService.findByUserId("test1");
        model.addAttribute("myuser", findUser);
        return "member/mypage";
    }


    //마이페이지 수정처리
    @PostMapping("/mypage/modify")
    @ResponseBody
    public String mypagemodify(@RequestBody MemberDTO memberDTO){
        log.info("mypate modify post - memberDTO : {}", memberDTO);
        //memberService.modifyMem(user.getUsrId());
        return "성공";
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



    //회원 탈퇴
    @GetMapping("/mypage/delete")
    public String deletemember(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        memberService.deleteMem(user.getUsrId());
        return "/index";
    }


    //회원 탈퇴 처리 !!
    @PostMapping("/idPwAvailAjax")
    @ResponseBody
    public ResponseEntity<Map<String, String>> idPwAvailAjax(String passwd, HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        Map<String, String> map = new HashMap<>();
        MemberDTO memberDTO = memberService.idPwCheck(user.getUsrId(), passwd);
        boolean result = false;
        if(memberDTO != null){
            result = true;
            memberService.deleteMember(user.getUsrId());
            session.invalidate(); //세션 아웃
        }
        model.addAttribute("result", result);
        return ResponseEntity.ok(map);
    }


//나의 판매 리스트 보기
    @GetMapping("/mypage/orderList")
    public String mypageorderlist(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        memberService.selectOrderList(user.getUsrId());
        return "/member/mypageList";
    }

    //나의 구매 리스트 보기
    @GetMapping("/mypage/purchaseList")
    public String mypagepurchaselist(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
       // memberService.selectTradeList(user.getUsrId());
        return "/member/mypageList";
    }


    //나의 신고내역 !!
    @GetMapping("/mypage/complist")
    public String myComplaint(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        List<ComplaintDTO> cmpList = memberService.findComplaintListByUsrId(user.getUsrId());
        model.addAttribute("cmpList", cmpList);
        return "member/mycomplaint";
    }
}
