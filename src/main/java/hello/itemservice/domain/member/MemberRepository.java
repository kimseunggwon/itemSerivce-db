package hello.itemservice.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemberRepository {

    private static Map<Long,Member> store = new HashMap<>();

    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save : member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(i-> i.getLoginId().equals(loginId))
                .findFirst(); // filter 조건에 일치하는 element 1개를 Optional로 리턴
                             // 조건에 일치하는 요소가 없다면 empty가 리턴
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
