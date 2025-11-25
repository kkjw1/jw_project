package spring.jwProject.repository.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import spring.jwProject.domain.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static spring.jwProject.domain.member.MemberLevel.NORMAL;
import static spring.jwProject.domain.member.MemberLevel.VIP;

@Repository
@Slf4j
public class MemoryMemberRepository implements MemberRepository{

    private static Map<String, Member> repository = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(1);

    @Override
    public Member save(Member member) {
        member.setMemberNo(sequence.getAndIncrement());
        member.setGrade(NORMAL);
        log.info("save member={}",member);
        return repository.put(member.memberId, member);

    }

    @Override
    public Member update(Member updateMember) {
        Member member = repository.get(updateMember.getMemberId());
        updateMember.setMemberNo(member.getMemberNo());
        repository.put(updateMember.getMemberId(), updateMember);
        log.info("update member={}", updateMember);
        return repository.get(updateMember.getMemberId());
    }

    @Override
    public String delete(String memberId) {
        Member deleteMember = repository.remove(memberId);
        return deleteMember.getMemberId();
    }

    @Override
    public Optional<Member> findById(String memberId) {
        Optional<Member> any = findAll().stream()
                .filter(m -> m.getMemberId().equals(memberId)).findAny();
        return any;
    }

    public List<Member> findAll() {
        return new ArrayList<>(repository.values());
    }

    //테스트용
    public void clearAll() {
        repository.clear();
    }

    //테스트용
    public void init() {
        Member memberA = new Member("memberA", "userA", "1234", "addressA", VIP);
        Member memberB = new Member("memberB", "userB", "1234", "addressB", NORMAL);
        this.save(memberA);
        this.save(memberB);
    }

    //테스트용
    public Long getMemberNo(String memberId) {
        Member member = repository.get(memberId);
        return member.getMemberNo();
    }
}
