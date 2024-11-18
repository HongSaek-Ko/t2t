package org.t2t.mem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.t2t.mem.dto.MemberDTO;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
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


    // 로그인 요청 처리
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> loginPost(@RequestParam String username,
                                                         @RequestParam String password, HttpServletRequest request) {
        // DB 무관하게 일단 로그인...
        Map<String, Object> response = new HashMap<>();
        MemberDTO dto = MemberDTO.builder()
                .usrId(username)
                .passwd(password)
                .logAtmCnt(1)
                .build();
        HttpSession session = request.getSession();
        session.setAttribute("user", dto);

        // 로그인 성공 응답
        response.put("success", true);
        response.put("username", username);
        response.put("password", password);

        return ResponseEntity.ok(response);
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
