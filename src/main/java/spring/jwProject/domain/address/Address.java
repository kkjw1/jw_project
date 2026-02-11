package spring.jwProject.domain.address;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.member.Member;

@Entity
@Getter
@SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "ADDRESS_SEQ", initialValue = 1, allocationSize = 1)
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ")
    @Column(name = "ADDRESS_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_NO")
    private Member member;

    private String addressName;

    // 기본배송지
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private String deliveryRequest;
    private Boolean mainAddress;


    public Address() {
    }

    public Address(Member member, String addressName, String postcode, String roadAddress, String detailAddress) {
        this.member = member;
        this.addressName = addressName;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }

    public Address(Member member, String postcode, String roadAddress, String detailAddress) {
        this.member = member;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }

    public void updateAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void updateMain(boolean mainAddress) {
        this.mainAddress = mainAddress;
    }
}
