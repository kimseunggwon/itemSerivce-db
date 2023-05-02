package hello.itemservice.web;

import hello.itemservice.domain.member.Member;
import hello.itemservice.service.LoginMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Logger;


@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginMemberService loginMemberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm form) {

        Member loginMember = loginMemberService.login(form.getLoginId(),form.getPassword());
        log.info("loginMember={}", loginMember);

        if (loginMember == null){
            return "login/loginForm";
        }

        System.out.println("정상 로그인");
        return "redirect:/";

    }

}
