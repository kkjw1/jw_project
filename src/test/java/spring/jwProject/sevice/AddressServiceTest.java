package spring.jwProject.sevice;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.address.Address;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.Member;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class AddressServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private AddressService service;

    @Test
    @DisplayName("주소 저장 테스트")
    public void saveAddressTest() throws Exception {
        //given
        Member member1 = new Member("member1", "member1@Email.com", "password", "name", Gender.MAN, "SKT",
                "010-1234-1234");
        Address address1 = new Address(member1, "기본배송지", "12341", "경기 성남시 분당구", "test");
        em.persist(member1);

        log.info("주소 저장");
        Address savedAddress = service.save(address1);

        Assertions.assertThat(address1).isEqualTo(savedAddress);
    }
}