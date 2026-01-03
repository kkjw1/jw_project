package spring.jwProject.domain.cart;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.item.Item;
import spring.jwProject.domain.member.Member;

@Entity
@Getter
public class Cart extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "CART_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
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
