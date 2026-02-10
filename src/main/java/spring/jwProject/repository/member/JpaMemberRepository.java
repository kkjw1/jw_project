package spring.jwProject.repository.member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.member.Member;
import spring.jwProject.validation.form.UpdateMember;

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

    @Override
    public Member update(UpdateMember updateMember) {
        Member member = findById(updateMember.id);
        if (member == null) {
            return null;
        }
        member.updateName(updateMember.getName());
        member.updateEmail(updateMember.getEmail());
        member.updatePassword(updateMember.getPassword());
        member.updateTelecom(updateMember.getTelecom());
        member.updatePhoneNumber(updateMember.getPhoneNumber());
        member.updateGender(updateMember.getGender());

        member.setLastModifiedBy(updateMember.getId());
        member.setLastModifiedDate(LocalDateTime.now());
        return member;
    }

    @Override
    public boolean delete(String memberId) {
        int result = em.createQuery("delete from Member m where m.id =:memberId")
                .setParameter("memberId", memberId)
                .executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }
}
