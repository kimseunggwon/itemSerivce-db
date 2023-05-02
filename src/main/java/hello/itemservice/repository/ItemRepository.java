package hello.itemservice.repository;

import hello.itemservice.domain.BookInfoVo;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond cond);

    void delete(Long id);

    List<Item> getItem();

}
