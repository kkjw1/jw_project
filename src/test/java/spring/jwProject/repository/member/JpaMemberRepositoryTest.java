package spring.jwProject.repository.member;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.Member;
import spring.jwProject.domain.member.MemberLevel;
import spring.jwProject.validation.form.UpdateMember;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class JpaMemberRepositoryTest {
    @Autowired EntityManager em;
    @Autowired MemberRepository repository;


    @Test
    @DisplayName("회원저장 테스트")
    public void signUpTest() throws Exception {
        //given
        Member member1 = new Member("member1", "member1@Email.com", "password", "name", Gender.MAN, "SKT",
                "010-1234-1234", "12345", "roadAddress", "detailAddress");
        Member member2 = new Member("member2", "member2@Email.com", "password", "name", Gender.WOMAN, "SKT",
                "010-1234-1234", "12345", "roadAddress", "detailAddress");

        //when
        Member savedMember1 = repository.save(member1);

        //then
        log.info("save member={}", member1);
        log.info("saved member={}", savedMember1);
        Assertions.assertThat(member1).isEqualTo(savedMember1);
    }
    
    @Test
    @DisplayName("아이디로 찾기 테스트")
    public void findByIdTest() throws Exception {
        //given
        Member member1 = new Member("member1", "member1@Email.com", "password", "name", Gender.MAN, "SKT",
                "010-1234-1234", "12345", "roadAddress", "detailAddress");
        Member member2 = new Member("member2", "member2@Email.com", "password", "name", Gender.WOMAN, "SKT",
                "010-1234-1234", "12345", "roadAddress", "detailAddress");
        em.persist(member1);
        em.persist(member2);
        em.flush();
        em.clear();

        //when
        log.info("아이디 찾기 실패");
        Member findMember = repository.findById("member3");
        log.info("아이디 찾기 성공");
        Member findMember2 = repository.findById("member2");

        //then
        Assertions.assertThat(findMember).isNull();
        Assertions.assertThat(findMember2.getNo()).isEqualTo(member2.getNo());
    }

    @Test
    @DisplayName("업데이트 테스트")
    @Commit
    public void updateTest() throws Exception {
        //given
        Member member1 = new Member("member1", "member1@Email.com", "password", "name", Gender.MAN, "SKT",
                "010-1234-1234", "12345", "roadAddress", "detailAddress");
        Member member2 = new Member("member2", "member2@Email.com", "password", "name", Gender.WOMAN, "SKT",
                "010-1234-1234", "12345", "roadAddress", "detailAddress");
        em.persist(member1);
        em.persist(member2);
        em.flush();
        em.clear();

        //when
        log.info("업데이트 성공");
        UpdateMember updateMember = new UpdateMember("member1", "update", "update", "update", "update", "update", Gender.MAN);
        Member update = repository.update(updateMember);
        Assertions.assertThat(update.getName()).isEqualTo("update");


        log.info("업데이트 실패");
        UpdateMember updateMember2 = new UpdateMember("member3", "update", "update", "update", "update", "update", Gender.MAN);
        Member update1 = repository.update(updateMember2);
        Assertions.assertThat(update1).isNull();

    }
}