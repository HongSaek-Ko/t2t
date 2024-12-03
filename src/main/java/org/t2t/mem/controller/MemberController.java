package org.t2t.mem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.t2t.mem.dto.*;
import org.t2t.mem.repository.ProfileMapper;
import org.t2t.mem.service.MemberService;
import org.t2t.mem.service.ProfileService;
import org.t2t.prd.dto.OrderDTO;
import org.t2t.prd.dto.ProductDTO;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j

public class MemberController {
    private final MemberService memberService;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @Value("${HTTP_SESSION_USER}")
    private String HTTP_SESSION_USER;

    @Value("${LOGGEDIN_MYUSER}")
    private String LOGGEDIN_MYUSER;

    //내 프로필 보기
    @GetMapping("/mypage")
    public String getMypage(HttpSession session, Model model) {
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        log.info("getmyPage: {}", user.toString());
        //세션에 저장된 usrId 가져오기
        //MemberDTO findUser = memberService.findByUserId(user.getUsrId());
        MemberDTO findUser = memberService.findByUserId(user.getUsrId());
        RankingDTO rankingDTO = memberService.selectRankByUsrId(findUser);
        findUser.setRank(rankingDTO);
        log.info("getmyPage: {}", findUser.toString());
        // 이미지 파일가져오기
        ProfileDTO imageProfile = profileMapper.selectFileList(user.getUsrId());
        log.info("getmyPage: {}", imageProfile.getProfImg().toString());

        findUser.setImageProfile(imageProfile);
        model.addAttribute(LOGGEDIN_MYUSER, findUser);
        return "member/mypage";
    }

    // 마이페이지 프로필 이미지(뷰) 요청
    @GetMapping("/mypage/image/{filename}")
    @ResponseBody
    public Resource getImages(@PathVariable("filename") String filename) throws MalformedURLException {
        log.info("GET /board/images - filename : {}", filename);
        return new UrlResource("file:" + profileService.getFullPath(filename));
    }



    // mile, rank 가짜데이터 체워주는 임시 메서드
    private void setTestData(MemberDTO findUser, String usrId) {
        MileDTO mileDTO = MileDTO.builder()
                .usrId(usrId)
                .point(1000L)
                .regDt(LocalDateTime.now())
                .lastDt(LocalDateTime.now())
                .build();

        RankingDTO rankDTO = RankingDTO.builder()
                .usrId(usrId)
                .score(1000L)
                .lastActDt(LocalDateTime.now())
                .regDt(LocalDateTime.now())
                .lastDt(LocalDateTime.now())
                .build();
        findUser.setMile(mileDTO);
        findUser.setRank(rankDTO);
    }


//    //마이페이지 수정처리
//    @PostMapping("/mypage/modify")
//    @ResponseBody
//    public ResponseEntity<Map<String, MemberDTO>> mypagemodify(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {
//        Map<String, MemberDTO> map = new HashMap<>();
//        log.info("mypate modify post - memberDTO : {}", memberDTO);
//        memberService.modifyMem(memberDTO);
//        // 세션정보 수정
//        HttpSession session = request.getSession();
//        MemberDTO findUser = memberService.findByUserId(memberDTO.getUsrId());
//        session.setAttribute(HTTP_SESSION_USER, findUser);
//        map.put("findUser", findUser);
//        //map.put("memberDTO", memberDTO.toString();
//
//        return ResponseEntity.ok(map);
//    }

    //비밀번호 변경
    @PostMapping("mypage/modify/passswd")
    @ResponseBody
    public ResponseEntity<String> changePassword(MemberDTO memberDTO, HttpSession session) throws NoSuchAlgorithmException {
        MemberDTO loggedUser = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        memberDTO.setUsrId(loggedUser.getUsrId());

        memberService.changePassword(memberDTO);
        return ResponseEntity.ok("success");
    }

    //포인트 충전
    @PostMapping("/mypage/charge")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addPoint(@RequestParam(name="point") Long point, HttpServletRequest request) {
        log.info("1");
        log.info(point.toString());

        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);

        memberService.updownMile(MileDTO.builder()
                .usrId(user.getUsrId())
                .point(point)
                .option(Trade.TRD01.getKey())
                .build());

        log.info("충전서비스");
        MileDTO totalMile = memberService.selectMileTotal(user.getUsrId()).get(0);
        // 로그인 성공 응답
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("mile", totalMile.getPoint());
        return ResponseEntity.ok(map);
    };


    //포인트 환전
    @PostMapping("/mypage/excharge")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> minPoint(@RequestParam(name="point") Long point, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);

        memberService.updownMile(MileDTO.builder()
                .usrId(user.getUsrId())
                .point(point)
                .option(Trade.TRD02.getKey())
                .build());

        MileDTO totalMile = memberService.selectMileTotal(user.getUsrId()).get(0);
        // 로그인 성공 응답
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("mile", totalMile.getPoint());
        return ResponseEntity.ok(map);
    };


    //나의 구매 리스트 보기
    @GetMapping("/mypage/orderList")
    public String mypageorderlist(HttpSession session, Model model) {
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        model.addAttribute("orderList", memberService.selectOrderList(user.getUsrId()));
        return "/member/mypageList";
    }

    //나의 현재 판매 리스트 보기
    @GetMapping("/mypage/CurTradeList")
    public String mypageCurTradeList(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        memberService.selectCurTradeList(user.getUsrId());
        return "member/mypageList";
    }

    //나의 과거 판매 리스트 보기
    @GetMapping("/mypage/PastTradeList")
    public String mypagePastTradeList(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        memberService.selectPastTradeList(user.getUsrId());
        return "member/mypageList";
    }

/*
    //나의 구매 리스트 보기
    @GetMapping("/mypage/OrderList")
    public String mypagepurchaselist(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        memberService.selectOrderList(user.getUsrId());
        return "/member/mypageList";
    }
*/

    //나의 신고내역
    @GetMapping("/mypage/complist")
    public String myComplaint(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        List<ComplaintDTO> cmpList = memberService.findComplaintListByUsrId(user.getUsrId());
        model.addAttribute("cmpList", cmpList);
        return "/member/mycomplaint";
    }

    //FAQ
    @GetMapping("/mypage/faq")
    public String Faq(HttpServletRequest request, Model model) {
        return  "/member/faq";
    }

    //나의 구매목록
    @GetMapping("/mypage/order")
    public String mypagepurchaselist(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO)session.getAttribute(HTTP_SESSION_USER);
        List<OrderDTO> orList = memberService.selectOrderList(user.getUsrId());

        model.addAttribute("orList", orList);
        return "/member/mypageList";
    }
}
