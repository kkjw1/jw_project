package spring.jwProject.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.delivery.Delivery;
import spring.jwProject.domain.item.Item;
import spring.jwProject.domain.member.Member;

@Entity
@Getter
@Table(name = "ORDERS")
public class Order extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    private OrderStatus orderStatus;
    private int count;
    private int totalPrice;
    private String postcode;
    private String roadAddress;
    private String detailAddress;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;
}
