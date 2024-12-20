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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.t2t.mem.dto.MainDTO;
import org.t2t.mem.dto.MemberDTO;
import org.t2t.mem.dto.MainFormDTO;
import org.t2t.mem.repository.MainMapper;
import org.t2t.mem.service.MainService;
import org.t2t.mem.service.MemberService;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Member;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private final MemberService memberService;


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
                createCookie(cid, cpw, Boolean.valueOf(cauto), response);
            }
        }
        return "product/list";
    }

    // 쿠키 생성 메서드
    private void createCookie(String id, String pw, boolean auto, HttpServletResponse response) {
        Cookie c1 = new Cookie("cid", id);  // 쿠키 객체 생성
        Cookie c2 = new Cookie("cpw", pw);
        Cookie c3 = new Cookie("cauto", Boolean.toString(auto));
        c1.setMaxAge(60 * 60); // 유효기간 설정 (예제는 1시간)
        c2.setMaxAge(60 * 60);
        c3.setMaxAge(60 * 60);
        response.addCookie(c1); // 응답객체에 쿠키 추가 -> 브라우저에 전송
        response.addCookie(c2);
        response.addCookie(c3);
    }

    // 로그인 처리 요청
    @PostMapping("/login")
    public String loginPro(@RequestParam(name="usrId") String usrId, @RequestParam(name="passwd") String passwd, @RequestParam(name="auto") boolean auto,
                           HttpServletResponse response, HttpSession session, Model model) throws NoSuchAlgorithmException, IOException {
        log.info("POST /login 로그인처리!!!");
        log.info("id: {}", usrId);

        boolean result = false;
        // 로그인 처리
        // DB에 사용자가 입력한 id와 pw가 일치하는 데이터가 있는지 확인
        // 가입시 "SHA-512"방식으로 비밀번호가 hashtext로 encoding 되었기 때문에
        // 로그인할 때도 비밀번호를 한번 encoding 해줘야 함 by Moon
        MainDTO mainDTO = mainMapper.idPwCheck(usrId, encode(passwd));
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if(mainDTO != null) {
            // 해당 아이디와 비밀번호가 있는지 확인
            if(mainDTO.getMemStat().equals("MEM01")) {
                // 해당 멤벙의 MemStat가 MEM01(활동중)인지 확인하고 로그인 허용
                result = true; // 로그인결과 true로 지정
                session.setAttribute("sid", mainDTO.getUsrId()); // 사용자 id값 저장
                session.setAttribute(HTTP_SESSION_USER, mainDTO.toEntity());
                if(auto) { // 자동로그인 체크 했다면,
                    createCookie(usrId, passwd, auto, response);
                }
            }
            else if(mainDTO.getMemStat().equals("MEM02")) {
                out.println("<script>alert('휴면상태 회원입니다 !!!'); history.go(-1);</script>");
                out.flush();
                response.flushBuffer();
                out.close();
                return "redirect:/login";
            }
            else if(mainDTO.getMemStat().equals("MEM03")) {
                out.println("<script>alert('활동정지 상태 회원입니다 !!!'); history.go(-1);</script>");
                out.flush();
                response.flushBuffer();
                out.close();
                return "redirect:/login";
            }
            else if(mainDTO.getMemStat().equals("MEM04")) {
                out.println("<script>alert('탈퇴한 회원입니다 !!!'); history.go(-1);</script>");
                out.flush();
                response.flushBuffer();
                out.close();
                return "redirect:/login";
            }
            else if(mainDTO.getMemStat().equals("MEM05")) {
                out.println("<script>alert('비밀번호 5회 초과했습니다 !!!'); history.go(-1);</script>");
                out.flush();
                response.flushBuffer();
                out.close();
                return "redirect:/login";
            }
        }
        // 일치X -> 일치하지 않다는 결과 화면에 주기
        model.addAttribute("result", result); // 화면에 id,pw 검사결과 전달
        return "loginPro"; // 로그인 결과 페이지
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr) {

//        log.info("sid1: {}", session.getId());
        session.removeAttribute(HTTP_SESSION_USER);
        session.invalidate(); // 세션 속성 모두 삭제
//        log.info("sid2: {}", session.getId());
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
        rttr.addFlashAttribute("numForAlert", 1);
        return "redirect:/";  // 홈으로 강제 이동
    }

    // 회원 가입 처리 요청 by moon
    @PostMapping("/guest/signup")
    public String newMemberPro(@ModelAttribute MainFormDTO member, RedirectAttributes rttr) throws IOException, NoSuchAlgorithmException {
        log.info("mainFormDTO: {}", member);

        // DB 저장
        int insertMember = mainService.insertMember(member);
        rttr.addFlashAttribute("insertMember", insertMember);
        log.info("insertMember: {}", insertMember);

        return "redirect:/";
    }

    // id 중복 확인 ajax 요청
    @PostMapping("/idAvailAjax")   // html 화면 결과가 아닌 데이터 응답
    @ResponseBody    // -> 보던 화면에 데이터를 body 부분에 담아서 응답

    public ResponseEntity<Map<String, String>> idAvailAjax(@RequestParam(name="usrId") String usrId) {
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

    // 비밀번호를 "SHA-512"방식을 이용하여 hashtext로 인코딩하는 메서드
    private String encode(String passwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        // digest() method is called
        // to calculate message digest of the input string
        // returned as array of byte
        byte[] messageDigest = md.digest(passwd.getBytes());
        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);
        // Convert message digest into hex value
        String hashtext = no.toString(16);
        log.info(hashtext);
        // Add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

//    // 회원 탈퇴 폼 요청
//    @GetMapping("/member/{id}/delete")
//    public String deleteMember(@PathVariable("id") String usrId) {
//        log.info("deleteForm id: {}", usrId);
//        return "/members/delete";
//    }

    // 회원 탈퇴 처리 요청
    @Transactional
    @PostMapping("/member/{usrId}/delete")
    public String deleteMemberPro(@PathVariable("usrId") String usrId, @RequestParam(name="passwd") String passwd, Model model, HttpSession session) throws NoSuchAlgorithmException {
        log.info("deletePro id: {}", usrId);
        log.info("deletePro pw: {}", passwd);

        // id와 비번 맞는지 확인
        MainDTO mainDTO = mainMapper.idPwCheck(usrId, encode(passwd));
        boolean result = false;
        if (mainDTO != null) {
            // 맞으면 탈퇴처리 -> 결과 출력
            result = true;
            mainMapper.deleteMember(usrId);// 탈퇴

//            mainMapper.deleteMemberFromProfile(usrId);      // 회원 탈퇴(Profile 테이블에서 관련 레코드(행) 삭제)
//            mainMapper.deleteMemberFromMiles(usrId);      // 회원 탈퇴(Miles 테이블에서 관련 레코드(행) 삭제)
//            mainMapper.deleteMemberFromRanking(usrId);     // 회원 탈퇴(Ranking 테이블에서 관련 레코드(행) 삭제)
            session.invalidate();      // 로그아웃 처리
        }

        // 틀리면 결과 출력
        model.addAttribute("result", result);
        return "/deletePro";
    }



    //마이페이지 수정처리
    @PostMapping("/member/mypage/modify")
    @ResponseBody
    public ResponseEntity<Map<String, MemberDTO>> mypagemodify(MainFormDTO mainFormDTO, HttpServletRequest request) throws IOException {
        Map<String, MemberDTO> map = new HashMap<>();
        log.info("mypage modify post - memberDTO : {}", mainFormDTO);
        memberService.modifyMem(mainFormDTO);
        // 세션정보 수정
        HttpSession session = request.getSession();
        MemberDTO findUser = memberService.findByUserId(mainFormDTO.getUsrId());
        session.setAttribute(HTTP_SESSION_USER, findUser);
        map.put("findUser", findUser);
        //map.put("memberDTO", memberDTO.toString();

        return ResponseEntity.ok(map);
    }

}
