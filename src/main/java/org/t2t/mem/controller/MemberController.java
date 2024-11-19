package org.t2t.mem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.mem.repository.MemberMapper;
import org.t2t.mem.service.MemberService;
import org.t2t.mem.service.MileDTO;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j

public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
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

    //포인트 충전
    @GetMapping("/member/mypage/charge/point")
    public String addPoint(@PathVariable("point") Long point, Model model, HttpServletRequest request, HttpSession httpSession) {
        HttpSession session = request.getSession();

        memberMapper.addMile(5000L);
    return "redirect:/mypage";
    };

}
