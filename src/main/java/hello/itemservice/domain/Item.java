package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity    // JPA가 사용하는 객체
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)    //pk 값을 생성해주는 기능  // IDENTITY => db에서 값을 넣어주는 거
    private Long id;

    @Column(name = "item_name", length = 10)
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
