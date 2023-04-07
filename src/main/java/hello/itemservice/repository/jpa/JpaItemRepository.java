package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Repository
@Transactional // JPA 모든 데이터 변경(등록,수정,삭제)은 Transactional안에서 이루어진다. 그래서 항상 Transactional 있어야 한다.
              // @Transactional는 메서드 끝날때 DB에 커밋날라감
public class JpaItemRepository implements ItemRepository {

    /**   JPA
     */
    private final EntityManager em;   //Spring을 통해 EntityManager를 주입받는다 -> JPA모든 동작은 EntityManager에서 이루어진다.

    public JpaItemRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = em.find(Item.class, itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());

        //JPA가 @Transactional 커밋되는 시점에 entity 변경된거를 기반으로 update쿼리를 DB에 날리므로, 따로 저장을 안해도 된다.
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }


    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        // jpql 문법은 SQL이랑 거의 비슷하지만 테이블 대상으로 하는게 아닌 Item 즉 Entity 대상으로 한다

        String jpql = "select i from Item i";
        Integer maxPrice = cond.getMaxPrice();
        String itemName = cond.getItemName();

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            jpql += " where";
        }
        boolean andFlag = false;
        if (StringUtils.hasText(itemName)) {
            jpql += " i.itemName like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                jpql += " and";
            }
            jpql += " i.price <= :maxPrice";
        }

        log.info("jpql={}", jpql);

        TypedQuery<Item> query = em.createQuery(jpql, Item.class);
        if (StringUtils.hasText(itemName)) {
            query.setParameter("itemName", itemName);
        }

        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }
        return query.getResultList();
    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Item> getItem() {
        return null;
    }
}
