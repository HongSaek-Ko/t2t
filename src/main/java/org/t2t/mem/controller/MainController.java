package org.t2t.mem.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.t2t.mem.dto.MainDTO;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.mem.dto.MainFormDTO;
import org.t2t.mem.repository.MainMapper;
import org.t2t.mem.service.MainService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    @Value("${HTTP_SESSION_USER}")
    private String HTTP_SESSION_USER;

    private final MainService mainService;
    private final MainMapper mainMapper;



    @GetMapping("/testuser")
    public String getTestLogin(HttpServletRequest request) {
        MemberDTO dto = MemberDTO.builder()
                .usrId("test2")
                .logAtmCnt(1)
                .build();

        HttpSession session = request.getSession();
        session.setAttribute(HTTP_SESSION_USER, dto);
        return "redirect:/";
    }


    @GetMapping("/")
    public String getHome(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Cookie[] cookies = request.getCookies();
        String cid = null, cpw = null, cauto = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cid")) {  // 쿠키가 있다
                    cid = cookie.getValue();
                }
                if (cookie.getName().equals("cpw")) {  // 쿠키가 있다
                    cpw = cookie.getValue();
                }
                if (cookie.getName().equals("cauto")) {  // 쿠키가 있다
                    cauto = cookie.getValue();
                }
            }
        }

        if(cid != null && cpw != null && cauto != null) {
            // 로그인 처리 : session sid 추가, 쿠키 갱신
            MainDTO mainDTO = mainMapper.idPwCheck(cid, cpw);
            if(mainDTO != null) {
                session.setAttribute("sid", cid);
                // 쿠키 갱신(같은 쿠키 만들어 전송 )
                createCookie(cid, cpw, cauto, response);
            }
        }
        return "index";
    }

    // 쿠키 생성 메서드
    private void createCookie(String id, String pw, String auto, HttpServletResponse response) {
        Cookie c1 = new Cookie("cid", id);  // 쿠키 객체 생성
        Cookie c2 = new Cookie("cpw", pw);
        Cookie c3 = new Cookie("cauto", auto);
        c1.setMaxAge(60 * 60); // 유효기간 설정 (예제는 1시간)
        c2.setMaxAge(60 * 60);
        c3.setMaxAge(60 * 60);
        response.addCookie(c1); // 응답객체에 쿠키 추가 -> 브라우저에 전송
        response.addCookie(c2);
        response.addCookie(c3);
    }

    // 로그인 처리 요청
    @PostMapping("/login")
    public String loginPro(String usrId, String passwd, String auto, HttpServletResponse response, HttpSession session, Model model) {
        log.info("POST /login 로그인처리!!!");
        log.info("id: {}", usrId);
        log.info("pw: {}", passwd);
        log.info("auto: {}", auto); // 체크했으면 auto, 체크안했으면 null
        // 로그인 처리
        // DB에 사용자가 입력한 id와 pw가 일치하는 데이터가 있는지 확인
        boolean result = false;
        MainDTO mainDTO = mainMapper.idPwCheck(usrId, passwd);
        if(mainDTO != null) {
            // 일치한다 -> 로그인 처리 -> 로그인 잘됐다는 결과 화면에 주기
            result = true; // 로그인결과 true로 지정
            session.setAttribute("sid", mainDTO.getUsrId()); // 사용자 id값 저장
            session.setAttribute(HTTP_SESSION_USER, mainDTO); // 사용자 id값 저장


            if(auto != null) { // 자동로그인 체크 했다면,
                createCookie(usrId, passwd, auto, response);
            }
        }
        // 일치X -> 일치하지 않다는 결과 화면에 주기
        model.addAttribute("result", result); // 화면에 id,pw 검사결과 전달
        return "loginPro"; // 로그인 결과 페이지
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.removeAttribute(HTTP_SESSION_USER);
        session.invalidate(); // 세션 속성 모두 삭제
        // 쿠키 삭제
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("cid") || cookie.getName().equals("cpw") || cookie.getName().equals("cauto")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        return "redirect:/";  // 홈으로 강제 이동
    }

    // 회원 가입 처리 요청 by moon
    @PostMapping("/guest/signup")
    public String newMemberPro(@ModelAttribute MainFormDTO member) throws IOException {
        log.info("mainFormDTO: {}", member);
        // DB 저장
        mainService.insertMember(member);

        return "redirect:/";
    }

    // id 중복 확인 ajax 요청
    @PostMapping("/idAvailAjax")   // html 화면 결과가 아닌 데이터 응답
    @ResponseBody    // -> 보던 화면에 데이터를 body 부분에 담아서 응답

    public ResponseEntity<Map<String, String>> idAvailAjax(String usrId) {
        log.info("Ajax id: {}", usrId);
        Map<String, String> map = new HashMap<>();
        String result = null;
        MainDTO mainDTO = mainMapper.selectOne(usrId);
        if(mainDTO != null) {
            result = "사용중인 아이디입니다";
            map.put("success", "false");
        } else {
            result = "사용가능한 아이디 입니다";
            map.put("success", "true");
        }
        map.put("result", result);

        return ResponseEntity.ok(map);
    }

    // ID 찾기 기능 추가 by Moon
    // ajax 요청(common.js에 ajax 코드 추가) : 데이터만 주고 받기
    @PostMapping("/findIdCheck")
    public ResponseEntity<String> searchId(@RequestBody MainDTO mainDTO) {
        String result = "";
        try {
            MainDTO findIdDTO = mainService.findMemberId(mainDTO);
            if(findIdDTO == null){ // DB에 없다
                result = "조회결과가 없습니다.";
            }else{
                result = findIdDTO.getUsrId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "index";
        return ResponseEntity.ok(result);
    }

    // 비밀번호 찾기 기능 추가 by Moon
    // 임시 비빌번호 발금으로 처리
    // ajax 요청(common.js에 ajax 코드 추가) : 데이터만 주고 받기
    @PostMapping("/findPwdResult")
    public ResponseEntity<String> findPwdCheck(@RequestBody MainDTO mainDTO) {
        String pwdResult = "";
        try {
            log.info(mainDTO.toString());
            int search = mainService.pwdCheck(mainDTO);
            log.info("search {}", search);
            if(search == 0) {
                pwdResult = "기입된 정보가 잘못되었습니다. 다시 입력해주세요.";
            } else {
                pwdResult = RandomStringUtils.randomAlphanumeric(10);
                mainDTO.setPasswd(pwdResult);
                log.info(mainDTO.getPasswd());
                mainService.pwdUpdate(mainDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(pwdResult);
//        return "member/findPwdResult";
    }



}
