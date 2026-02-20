package spring.jwProject.validation.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpSeller {
    @NotBlank(message = "아이디가 공백입니다.")
    private String id;
    @NotNull(message = "비밀번호가 공백입니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상이여야 합니다.")
    private String password;
    @Email(message = "이메일 주소가 정확한지 확인해 주세요.")
    private String email;
    @NotBlank(message = "담당자 이름이 공백입니다.")
    private String name;
    @NotBlank(message = "전화번호가 공백입니다.")
    private String phoneNumber;
    @NotBlank(message = "회사명이 공백입니다.")
    private String companyName;

    private String companyPhone;
    @NotBlank(message = "회사 주소가 공백입니다.")
    private String postcode;
    @NotBlank
    private String roadAddress;
    private String detailAddress;

    public SignUpSeller() {
    }

    public SignUpSeller(String id, String password, String email, String name, String phoneNumber, String companyName, String companyPhone, String postcode, String roadAddress, String detailAddress) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.companyPhone = companyPhone;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }
}
