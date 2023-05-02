package hello.itemservice.repository.memory;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.member.Member;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static

    private static final Map<Long, Member> memberStore = new HashMap<>();
    private static long sequence = 0L; //static

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        return store.values().stream()
                .filter(item -> {
                    if (ObjectUtils.isEmpty(itemName)) {  //itemName 없으면 검색조건 안쓴다
                        return true;
                    }
                    return item.getItemName().contains(itemName); // itemName 값이 있으면 필터링 기능 수행
                }).filter(item -> {
                    if (maxPrice == null) {
                        return true;
                    }
                    return item.getPrice() <= maxPrice;  // 가격이 있으면 맥스보다 작은 데이터만 통과
                })
                .collect(Collectors.toList()); // collect 모아서  list도 반환
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    public void clearStore() {
        store.clear();
    } // 테스트 용도

    @Override
    public List<Item> getItem() {
        return null;
    }


    /**
     *   member
     */
    public Member saveMember(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        memberStore.put(member.getId(), member);
        return member;
    }

    public Member findByMember(Long id) {
        return memberStore.get(id);
    }

    public Optional<Member> findByLoginMember(String loginId) {
        return findAllMember().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAllMember() {
        return new ArrayList<>(memberStore.values());
    }

}
