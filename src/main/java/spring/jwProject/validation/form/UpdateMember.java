package spring.jwProject.validation.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateMember {

    public String memberId;

    @NotBlank(message = "이름이 공백입니다.")
    public String memberName;

    public String postcode;

    public String roadAddress;
    public String jibunAddress;
    public String detailAddress;
    public String extraAddress;


    public UpdateMember() {
    }

}
