package spring.jwProject.sevice;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.seller.Seller;
import spring.jwProject.repository.seller.SellerRepository;
import spring.jwProject.validation.form.LoginSeller;
import spring.jwProject.validation.form.SignUpSeller;

@SpringBootTest
@Transactional
@Slf4j
class SellerServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    SellerService sellerService;

    @Test
    @DisplayName("회원가입")
    public void test1() throws Exception {
        //given
        SignUpSeller signUpSeller = new SignUpSeller("test", "test", "test@email.com", "테스트", "010-0000-0000", "testCompany", "010-1111-1111", "11111", "테스트 주소", "테스트 상세주소");

        //when
        Seller seller = sellerService.signUp(signUpSeller);

        //then
        Assertions.assertThat(seller.getId()).isEqualTo("test");
    }


    @Test
    @DisplayName("아이디 중복 검사 - 아이디 중복 X")
    public void test2() throws Exception {
        //given
        SignUpSeller signUpSeller = new SignUpSeller("test", "test", "test@email.com", "테스트", "010-0000-0000", "testCompany", "010-1111-1111", "11111", "테스트 주소", "테스트 상세주소");
        Seller seller = sellerService.signUp(signUpSeller);

        //when
        boolean result = sellerService.checkId("testt");

        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("아이디 중복 검사 - 아이디 중복")
    public void test3() throws Exception {
        //given
        SignUpSeller signUpSeller = new SignUpSeller("test", "test", "test@email.com", "테스트", "010-0000-0000", "testCompany", "010-1111-1111", "11111", "테스트 주소", "테스트 상세주소");
        Seller seller = sellerService.signUp(signUpSeller);

        //when
        boolean result = sellerService.checkId("test");

        //then
        Assertions.assertThat(result).isFalse();
    }
    
    @Test
    @DisplayName("로그인")
    public void test4() throws Exception {
        //given
        SignUpSeller signUpSeller = new SignUpSeller("test", "test", "test@email.com", "테스트", "010-0000-0000", "testCompany", "010-1111-1111", "11111", "테스트 주소", "테스트 상세주소");
        Seller seller = sellerService.signUp(signUpSeller);

        //when
        log.info("로그인 성공");
        LoginSeller loginSeller = new LoginSeller("test", "test");
        Seller login = sellerService.login(loginSeller);

        //then
        Assertions.assertThat(login.getEmail()).isEqualTo("test@email.com");

        //when
        log.info("로그인 실패-비밀번호 에러");
        LoginSeller loginSeller2 = new LoginSeller("test", "tttt");
        Seller login2 = sellerService.login(loginSeller2);

        //then
        Assertions.assertThat(login2).isNull();

        //when
        log.info("로그인 실패-아이디 에러");
        LoginSeller loginSeller3 = new LoginSeller("tttt", "test");
        Seller login3 = sellerService.login(loginSeller3);

        //then
        Assertions.assertThat(login3).isNull();
    }
}