package sequence.sequence_member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sequence.sequence_member.dto.LoginDTO;
import sequence.sequence_member.dto.MemberDTO;
import sequence.sequence_member.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/api/member/join")
    public String join(@RequestBody MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "complete";
    }

    @PostMapping("/api/member/login")
    public String login(@ModelAttribute @RequestBody LoginDTO loginDTO, HttpSession session){
        LoginDTO loginResult =  memberService.loginCheck(loginDTO);
        if(loginResult != null) {
            session.setAttribute("user_id", loginResult.getUser_id());
            return "complete";
        }else return "index";
    }

}
