package hello.itemservice.service;

import hello.itemservice.domain.member.Member;
import hello.itemservice.repository.memory.MemoryItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Configuration
public class LoginMemberService {
    private final MemoryItemRepository memoryItemRepository;

    public Member login(String loginId, String password) {

        return memoryItemRepository.findByLoginMember(loginId)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }

}
