package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataJpaItemRepository extends JpaRepository<Item,Long > {

    // JpaRepository 인터페이스를 인터페이스 상속 받고, 제네릭에 관리할 <엔티티, 엔티티PK값> 를 주면 된다.
    // 그러면 JpaRepository 가 제공하는 기본 CRUD 기능을 모두 사용할 수 있다.

    List<Item> findByItemNameLike(String itemName);     // 이름 조회
    List<Item> findByPriceLessThanEqual(Integer price);  // 가격 조회

    //쿼리 메서드 (아래 메서드와 같은 기능 수행)
    List<Item> findByItemNameLikeAndPriceLessThanEqual(String itemName, Integer price); // 이름 + 가격 조회

    //쿼리 직접 실행
    @Query("select i from Item i where i.itemName like :itemName and i.price <= :price")
    List<Item> findItems(@Param("itemName") String itemName, @Param("price") Integer price); // 이름 + 가격 조회

}
