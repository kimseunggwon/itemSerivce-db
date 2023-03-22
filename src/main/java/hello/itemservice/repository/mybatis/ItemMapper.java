package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Mapper
// 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스이다.
// 이 인터페이스의 메서드를 호출하면  xml 의 해당 SQL을 실행하고 결과를 돌려준다.
public interface ItemMapper {

    void save(Item item);

    //@Param이 2개가 넘어가는 경우에는 붙여준다
    void update(@Param("id") Long id, @Param("updateParam")ItemUpdateDto itemUpdateDto);

    Optional<Item> findById(Long id);

    List<Item> findAll (ItemSearchCond itemSearchCond);

    void delete(Long id);

    List<Item> getItem();

    int Count();
}
