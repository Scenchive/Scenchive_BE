package com.example.scenchive.web;

import com.example.scenchive.domain.Member;
import com.example.scenchive.service.LoginService;
import com.example.scenchive.service.NotCorrespondingEmailException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    @ResponseBody //포스트맨 테스트용, 지워야함
    public String login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult) throws NotCorrespondingEmailException {
        if(bindingResult.hasErrors()){
            return "/loginform"; // 로그인 화면 URL 넣기
        }

        //로그인 성공
        Member loginMember=loginService.login(loginForm.getEmail(), loginForm.getPassword());

        //세션 생성
        if (loginMember==null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/loginform"; // 로그인 화면 URL 넣기
        }

        return "/home"; //메인화면 URL 넣기
    }
}