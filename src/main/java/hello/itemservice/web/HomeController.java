package hello.itemservice.web;

import hello.itemservice.domain.member.Member;
import hello.itemservice.repository.memory.MemoryItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemoryItemRepository memoryItemRepository;

    //@RequestMapping("/")
    public String home() {
        log.debug("home={}",home());
        return "redirect:/items";
    }

    @GetMapping("/home")
    public String home1() {
        return "home";
    }

    @GetMapping("")
    public String homeLogin(Long memberId, Model model) {

        if (memberId == null) {
            return "home";
        }

        Member loginMember = memoryItemRepository.findByMember(memberId);

        model.addAttribute("member",loginMember);
        return "loginHome";

    }

}
