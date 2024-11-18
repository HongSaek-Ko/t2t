package org.t2t.mem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.t2t.mem.dto.MemberDTO;

@Controller
public class MainController {
    @GetMapping("/testuser")
    public String getTestLogin(HttpServletRequest request) {
        MemberDTO dto = MemberDTO.builder()
                .usrId("test1")
                .logAtmCnt(1)
                .build();

        HttpSession session = request.getSession();
        session.setAttribute("user", dto);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getHome(HttpServletRequest request) {
        return "index";
    }
    @GetMapping("/testclose")
    public String getTestSessionClose(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
