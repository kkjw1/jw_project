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
@SequenceGenerator(name = "ORDERS_SEQ", sequenceName = "ORDERS_SEQ", initialValue = 1, allocationSize = 1)
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ")
    @Column(name = "ORDER_NO")
    private Long no;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "ITEM_NO")
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
