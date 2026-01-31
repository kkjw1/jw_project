package spring.jwProject.domain.seller;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.MemberLevel;

@Entity
@Getter
@SequenceGenerator(name = "SELLER_SEQ", sequenceName = "SELLER_SEQ", initialValue = 1, allocationSize = 1)
public class Seller extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SELLER_SEQ")
    @Column(name = "SELLER_NO")
    private Long no;

    private String id;
    private String password;
    private String email;
    private String name;
    private String phoneNumber;

    private String companyName;
    private String companyPhone;

    private String postcode;
    private String roadAddress;
    private String detailAddress;

}
