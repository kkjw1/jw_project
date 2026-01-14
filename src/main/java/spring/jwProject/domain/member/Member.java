package spring.jwProject.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;

@Entity
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_NO")
    private Long no;

    private String id;
    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String telecom;
    private String phoneNumber;

    private String postcode;
    private String roadAddress;
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    private MemberLevel level;


    public Member() {
    }

    public Member(String id, String email, String password, String name, Gender gender, String telecom, String phoneNumber, String postcode, String roadAddress, String detailAddress) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.telecom = telecom;
        this.phoneNumber = phoneNumber;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.level = MemberLevel.NORMAL;
    }

    public boolean passwordEquals(String password) {
        return this.password.equals(password);
    }
}
