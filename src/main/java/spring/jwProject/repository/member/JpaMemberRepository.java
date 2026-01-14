package spring.jwProject.repository.member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.member.Member;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    @Override
    public Member save(Member member) {
        member.setCreatedDate(LocalDateTime.now());
        member.setCreatedBy(member.getId());
        try {
            em.persist(member);
            return member;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Member findById(String id) {
        List<Member> findMembers = em.createQuery("select m from Member m where m.id =:id", Member.class)
                .setParameter("id", id)
                .getResultList();
        if (findMembers.isEmpty()) {
            return null;
        } else return findMembers.get(0);
    }
}
