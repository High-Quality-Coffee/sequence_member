package sequence.sequence_member.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sequence.sequence_member.dto.MemberDTO;
import sequence.sequence_member.response.ResponseMsg;
import sequence.sequence_member.service.MemberService;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/api/user")
    public ResponseEntity<ResponseMsg> join(@RequestBody @Valid MemberDTO memberDTO, Errors errors){
        //회원가입 유효성 검사 실패 시
        if(errors.hasErrors()){
            Map<String, String> validatorResult = memberService.validateHandling(errors);

            ResponseMsg errorResponse = new ResponseMsg(400, "Validation Failed", validatorResult);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        memberService.save(memberDTO);
        ResponseMsg responseMsg = new ResponseMsg(200, "회원가입이 완료되었습니다.", null);
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

    @RequestMapping("/api/check_username")
    public ResponseEntity<ResponseMsg> checkUser(@RequestParam(name = "username",required = false) String username){
        System.out.println(username);
        // 파라미터 유효성 검사
        if (username == null || username.trim().isEmpty()) {
            ResponseMsg responseMsg = new ResponseMsg(400, "아이디를 입력해주세요.", null);
            return ResponseEntity.badRequest().body(responseMsg);
        }

        //중복 아이디가 존재하는 경우
        if(memberService.checkUser(username)){
            ResponseMsg responseMsg = new ResponseMsg(400, "동일한 아이디가 이미 존재합니다.", null);
            return ResponseEntity.badRequest().body(responseMsg);
        }
        //아이디가 존재하지 않는 경우
        ResponseMsg responseMsg = new ResponseMsg(200, "사용가능한 아이디 입니다.", null);
        return ResponseEntity.ok(responseMsg);
    }

}
