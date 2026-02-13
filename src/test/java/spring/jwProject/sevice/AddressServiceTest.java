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
import spring.jwProject.validation.form.ManageAddress;

import java.util.List;

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
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("주소 저장 성공 - 회원가입")
    public void test1() throws Exception {
        //given
        Member member1 = new Member("qqqq", "qqqq", "qqqq", "qqqq", Gender.MAN, "SKT",
                "010-1234-1234");
        Address address1 = new Address(member1, "12341", "경기 성남시 분당구", "test", "test", "test", "010-1111-2222");

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
        ManageAddress address1 = new ManageAddress("기본배송지", "수령인이름","010-1111-2222",
                "12341", "경기 성남시 분당구", "101동", "요청사항1", true);
        address1.setMemberId("test");       // test라는 데이터가 이미 DB에 존재함


        //when
        Address saved = service.save(address1);
        List<Address> savedAddress = repository.findAddresses("test");
        Assertions.assertThat(savedAddress.size()).isEqualTo(4);
        Assertions.assertThat(savedAddress.get(0).getMember().getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("주소리스트 가져오기")
    public void test3() throws Exception {
        //given
        Member m1 = new Member("qqqq", "qqqq", "qqqq", "qqqq", Gender.MAN, "SKT", "010-1234-1234");
        Member m2 = new Member("wwww", "wwww", "wwww", "wwww", Gender.MAN, "KT", "010-2222-3333");
        em.persist(m1);
        em.persist(m2);
        Address a1 = new Address(m1, "주소1", "사람1", "010-1111-1111", "11111", "경기 성남시 분당구", "101호", "요청사항1", false);
        Address a3 = new Address(m1, "주소3", "사람3", "010-3333-3333", "33333", "경기 성남시 분당구", "303호", "요청사항3", false);
        Address a4 = new Address(m2, "주소4", "사람4", "010-4444-4444", "44444", "경기 성남시 분당구", "404호", "요청사항4", false);

        em.persist(a1);
        em.persist(a3);
        em.persist(a4);
        em.flush();
        em.clear();

        //when
        List<Address> addresses = repository.findAddresses(m1.getId());

        //then
        Assertions.assertThat(addresses.size()).isEqualTo(2);
        for (Address address : addresses) {
            log.info("address postcode: {}", address.getPostcode());
        }
    }

    @Test
    @DisplayName("배송지 저장 - 기본배송지로 저장")
    public void test4() throws Exception {
        //given
        ManageAddress address = new ManageAddress("테스트주소", "test", "010-1123-1321", "66666",
                "테스트 도로명", "테스트 상세주소", "테스트 배송요청", true);
        address.setMemberId("test");

        //when
        Address saved = service.save(address);

        //then
        List<Address> afterAddresses = repository.findAddresses("test");
        for (Address afterAddress : afterAddresses) {
//            log.info("[{},{}]", afterAddress.getAddressName(), afterAddress.getMainAddress());
            if (afterAddress.getAddressName().equals("테스트주소")) {
                Assertions.assertThat(afterAddress.getMainAddress()).isTrue();
            }
            if (afterAddress.getAddressName().equals("메인주소")) {
                Assertions.assertThat(afterAddress.getMainAddress()).isFalse();
            }
        }

    }

    @Test
    @DisplayName("주소 삭제 성공")
    public void test5() throws Exception {
        //given
        Member m1 = new Member("qqqq", "qqqq", "qqqq", "qqqq", Gender.MAN, "SKT", "010-1234-1234");
        Member m2 = new Member("wwww", "wwww", "wwww", "wwww", Gender.MAN, "KT", "010-2222-3333");
        em.persist(m1);
        em.persist(m2);
        Address a1 = new Address(m1, "주소1", "사람1", "010-1111-1111", "11111", "경기 성남시 분당구", "101호", "요청사항1", false);
        Address a3 = new Address(m1, "주소3", "사람3", "010-3333-3333", "33333", "경기 성남시 분당구", "303호", "요청사항3", false);
        Address a4 = new Address(m2, "주소4", "사람4", "010-4444-4444", "44444", "경기 성남시 분당구", "404호", "요청사항4", false);
        em.persist(a1);
        em.persist(a3);
        em.persist(a4);
        em.flush();
        em.clear();

        //when
        List<Address> addresses = service.getAddresses("qqqq");
        Long deleteAddressNo = addresses.get(0).getNo();
        boolean result = service.delete(deleteAddressNo);
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(service.getAddresses("qqqq").size()).isEqualTo(1);
    }

    
    @Test
    @DisplayName("주소 삭제 실패-연속으로 삭제")
    public void test6() throws Exception {
        //given
        Member m1 = new Member("qqqq", "qqqq", "qqqq", "qqqq", Gender.MAN, "SKT", "010-1234-1234");
        Member m2 = new Member("wwww", "wwww", "wwww", "wwww", Gender.MAN, "KT", "010-2222-3333");
        em.persist(m1);
        em.persist(m2);
        Address a1 = new Address(m1, "주소1", "사람1", "010-1111-1111", "11111", "경기 성남시 분당구", "101호", "요청사항1", false);
        Address a3 = new Address(m1, "주소3", "사람3", "010-3333-3333", "33333", "경기 성남시 분당구", "303호", "요청사항3", false);
        Address a4 = new Address(m2, "주소4", "사람4", "010-4444-4444", "44444", "경기 성남시 분당구", "404호", "요청사항4", false);
        em.persist(a1);
        em.persist(a3);
        em.persist(a4);
        em.flush();
        em.clear();

        List<Address> addresses = service.getAddresses("qqqq");
        Long deleteAddressNo = addresses.get(0).getNo();
        service.delete(deleteAddressNo);
        boolean result2 = service.delete(deleteAddressNo);
        Assertions.assertThat(result2).isFalse();
        Assertions.assertThat(service.getAddresses("qqqq").size()).isEqualTo(1);
    }
}