package spring.jwProject.sevice;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.Member;

@SpringBootTest
@Slf4j
@Transactional
class MemberServiceTest {
    @Autowired
    EntityManager em;
    @Autowired MemberService service;

    @Test
    @DisplayName("로그인테스트")
    public void loginTest() throws Exception {
        //given
        Member member1 = new Member("member1", "member1@Email.com", "password", "name", Gender.MAN, "SKT",
                "010-1234-1234", "12345", "roadAddress", "detailAddress");
        Member member2 = new Member("member2", "member2@Email.com", "password", "name", Gender.WOMAN, "SKT",
                "010-1234-1234", "12345", "roadAddress", "detailAddress");
        em.persist(member1);
        em.persist(member2);
        em.flush();
        em.clear();

        log.info("로그인 성공");
        String id1 = "member1";
        String password1 = "password";
        Member login = service.login(id1, password1);
        Assertions.assertThat(login.getNo()).isEqualTo(member1.getNo());

        log.info("로그인 실패, 아이디 불일치");
        String id2 = "member3";
        String password2 = "password";
        Member loginFail = service.login(id2, password2);
        Assertions.assertThat(loginFail).isNull();

        log.info("로그인 실패, 비밀번호 불일치");
        String id3 = "member2";
        String password3 = "password2";
        Member loginFail2 = service.login(id3, password3);
        Assertions.assertThat(loginFail2).isNull();
    }
}