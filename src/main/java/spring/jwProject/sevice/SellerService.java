package spring.jwProject.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.jwProject.domain.member.Member;
import spring.jwProject.domain.seller.Seller;
import spring.jwProject.repository.seller.SellerRepository;
import spring.jwProject.validation.form.LoginSeller;
import spring.jwProject.validation.form.SignUpSeller;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository repository;


    /**
     * 판매자 회원가입
     * @param signUpSeller
     * @return 성공:Seller, 실패:null
     */
    public Seller signUp(SignUpSeller signUpSeller) {
        Seller seller = new Seller(
                signUpSeller.getId(),
                signUpSeller.getPassword(),
                signUpSeller.getEmail(),
                signUpSeller.getName(),
                signUpSeller.getPhoneNumber(),
                signUpSeller.getCompanyName(),
                signUpSeller.getCompanyPhone(),
                signUpSeller.getPostcode(),
                signUpSeller.getRoadAddress(),
                signUpSeller.getDetailAddress()
        );

        return repository.save(seller);
    }

    /**
     * 아이디 중복 검사
     * @return 중복아니면 true, 중복이면 false
     */
    public boolean checkId(String sellerId) {
        return repository.findById(sellerId) == null;
    }

    /**
     * 판매자 로그인
     * @param loginSeller
     * @return 성공:Seller, 실패:null
     */
    public Seller login(LoginSeller loginSeller) {
        Seller seller = repository.findById(loginSeller.getId());
        if (seller != null && seller.getPassword().equals(loginSeller.getPassword())) {
            return seller;
        } else {
            return null;
        }
    }
}
