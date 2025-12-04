package spring.jwProject.domain.seller;

import lombok.Data;
import spring.jwProject.domain.member.MemberLevel;

@Data
public class Seller {
    public Long sellerNo;
    public String memberId;
    public String memberName;
    public String password;
    public MemberLevel grade;


    public String postcode;
    public String roadAddress;
    public String jibunAddress;
    public String detailAddress;
    public String extraAddress;
}
