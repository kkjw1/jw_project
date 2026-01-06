package spring.jwProject.domain.seller;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.MemberLevel;

@Entity
@Getter
public class Seller extends BaseEntity {
    @Id
    @GeneratedValue
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
