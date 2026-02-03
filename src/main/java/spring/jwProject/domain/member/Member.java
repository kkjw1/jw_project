package spring.jwProject.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import spring.jwProject.domain.BaseEntity;
import spring.jwProject.domain.address.Address;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(name = "MEMBER_SEQ", sequenceName = "MEMBER_SEQ", initialValue = 1, allocationSize = 1)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ")
    @Column(name = "MEMBER_NO")
    private Long no;

    private String id;
    private String email;
    private String password;
    private String name;

    private String telecom;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MemberLevel memberLevel;

    @OneToMany(mappedBy = "member")
    private List<Address> addresses = new ArrayList<>();

    public Member() {
    }

    public Member(String id, String email, String password, String name, Gender gender, String telecom, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.telecom = telecom;
        this.phoneNumber = phoneNumber;
        this.memberLevel = MemberLevel.NORMAL;
    }

    public boolean passwordEquals(String password) {
        return this.password.equals(password);
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateTelecom(String telecom) {
        this.telecom = telecom;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateMemberLevel(MemberLevel memberLevel) {
        this.memberLevel = memberLevel;
    }
}
