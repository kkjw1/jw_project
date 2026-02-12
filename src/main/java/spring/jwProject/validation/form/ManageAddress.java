package spring.jwProject.validation.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import spring.jwProject.domain.member.Member;

@Getter @Setter
public class ManageAddress {
    private String addressName;

    @NotBlank(message = "수령인 이름이 비어있습니다.")
    private String recipientName;
    @NotBlank(message = "휴대폰 번호가 비어있습니다.")
    private String phoneNumber;

    @NotBlank(message = "주소가 비어있습니다.")
    private String postcode;
    @NotBlank(message = "주소가 비어있습니다.")
    private String roadAddress;
    private String detailAddress;
    private String deliveryRequest;

    private Boolean mainAddress;

    private String memberId;

    public ManageAddress() {
    }

    public ManageAddress(String addressName, String recipientName, String phoneNumber, String postcode, String roadAddress, String detailAddress, String deliveryRequest, Boolean mainAddress) {
        this.addressName = addressName;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.deliveryRequest = deliveryRequest;
        this.mainAddress = mainAddress;
    }
}
