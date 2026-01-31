package spring.jwProject.domain.delivery;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.order.Order;

@Entity
@Getter
@SequenceGenerator(name = "DELIVERY_SEQ", sequenceName = "DELIVERY_SEQ", initialValue = 1, allocationSize = 1)
public class Delivery extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ")
    @Column(name = "DELIVERY_NO")
    private Long no;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_NO")
    private Order order;

    private String courier;
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String recipientName;
    private String recipientPhone;

    private int shippingCost;
}
