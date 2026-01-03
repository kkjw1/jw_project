package spring.jwProject.domin;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.Member;
import spring.jwProject.domain.member.MemberLevel;

@SpringBootTest
@Transactional
public class EntityTest {
    @Autowired
    EntityManager em;

    @Test
    @Commit
    public void EntityTest() throws Exception {
        //given
        Member member = new Member("member1@email.com","password","name", Gender.MAN, "SKT", "010-1234-1234",
                "12345", "Address", "detail", MemberLevel.NORMAL);
        em.persist(member);

        //when

        //then

    }
}
