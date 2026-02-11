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
import spring.jwProject.repository.address.AddressRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class AddressServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private AddressService service;
    @Autowired
    private AddressRepository repository;

    @Test
    @DisplayName("주소 저장 성공 - 회원가입")
    public void test1() throws Exception {
        //given
        Member member1 = new Member("qqqq", "qqqq", "qqqq", "qqqq", Gender.MAN, "SKT",
                "010-1234-1234");
        Address address1 = new Address(member1, "12341", "경기 성남시 분당구", "test");

        //when
        em.persist(member1);
        Address savedAddress = service.signUp(address1);

        //then
        Assertions.assertThat(address1).isEqualTo(savedAddress);
        Assertions.assertThat(savedAddress.getMainAddress()).isTrue();
    }

    @Test
    @DisplayName("주소 저장 성공 - 주소 추가")
    public void test2() throws Exception {
        //given
        Member member1 = new Member("qqqq", "qqqq", "qqqq", "qqqq", Gender.MAN, "SKT",
                "010-1234-1234");
        em.persist(member1);
        em.flush();
        em.clear();

        //when
        Address address1 = new Address(member1, "기본배송지", "12341", "경기 성남시 분당구", "test");

        Address savedAddress = service.save(address1);
        Assertions.assertThat(savedAddress).isEqualTo(address1);
    }

    @Test
    @DisplayName("주소리스트 가져오기")
    public void test3() throws Exception {
        //given
        Member m1 = new Member("qqqq", "qqqq", "qqqq", "qqqq", Gender.MAN, "SKT", "010-1234-1234");
        Member m2 = new Member("wwww", "wwww", "wwww", "wwww", Gender.MAN, "KT", "010-2222-3333");
        em.persist(m1);
        em.persist(m2);
        Address a1 = new Address(m1, "11111", "경기 성남시 분당구", "1번째 주소");
        Address a2 = new Address(m1, "22222", "경기 성남시 분당구", "2번째 주소");
        Address a3 = new Address(m1, "33333", "경기 성남시 분당구", "3번째 주소");
        Address a4 = new Address(m2, "44444", "경기 성남시 분당구", "1번째 주소");
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);
        em.flush();
        em.clear();

        //when
        List<Address> addresses = repository.findAddresses(m1.getId());

        //then
        Assertions.assertThat(addresses.size()).isEqualTo(3);
        for (Address address : addresses) {
            log.info("address postcode: {}", address.getPostcode());
        }
    }
}