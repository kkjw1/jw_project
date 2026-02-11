package spring.jwProject.domain.cart;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.item.Item;
import spring.jwProject.domain.member.Member;

@Entity
@Getter
@SequenceGenerator(name = "CART_SEQ",sequenceName = "CART_SEQ", initialValue = 1, allocationSize = 1)
public class Cart extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_SEQ")
    @Column(name = "CART_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int count;
    private int totalPrice;

    public Cart() {
    }

    //totalPrice 계산
    public void setTotalPrice() {

    }
}
