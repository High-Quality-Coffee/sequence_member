package sequence.sequence_member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sequence.sequence_member.dto.MemberDTO;
import sequence.sequence_member.response.ResponseMsg;
import sequence.sequence_member.service.MemberService;

import java.util.Map;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/api/user")
    public ResponseEntity<ResponseMsg> join(@Valid @RequestBody MemberDTO memberDTO, Errors errors){
        //회원가입 유효성 검사 실패 시
        if(errors.hasErrors()){
            Map<String, String> validatorResult = memberService.validateHandling(errors);

            ResponseMsg errorResponse = new ResponseMsg(400, "Validation Failed", validatorResult);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        memberService.save(memberDTO);
        ResponseMsg responseMsg = new ResponseMsg(200, "ok", null);

        return ResponseEntity.ok(responseMsg);
    }

    @GetMapping("/api/test")
    public String test(){
        return "this is test api controller";
    }

    //시큐리티 컨텍스트에 저장된 authtoken을 확인하는 용도로 컨트롤러 작성
    //jwt는 stateless이지만, 생성되었을때, 세션을 통해 잠시 저장되었다가 삭제된다. (stateless로 봐도 무방하다)
    @GetMapping("/")
    public String mainPage(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return "mainPage " + username;
    }

}
