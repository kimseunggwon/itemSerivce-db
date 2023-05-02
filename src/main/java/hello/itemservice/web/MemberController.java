package hello.itemservice.web;

import hello.itemservice.domain.member.Member;
import hello.itemservice.repository.memory.MemoryItemRepository;
import hello.itemservice.service.LoginMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemoryItemRepository memoryItemRepository;
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member")Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Member member) {

        memoryItemRepository.saveMember(member);
        log.info("memoryItemRepository={}", memoryItemRepository.saveMember(member));
        return "redirect:/";
    }

}
