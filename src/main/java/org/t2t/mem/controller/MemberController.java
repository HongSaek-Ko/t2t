package org.t2t.mem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.mem.service.MemberService;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
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
}
