package spring.jwProject.repository.member;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;
import spring.jwProject.domain.BeforeMember;
import spring.jwProject.domain.MemberLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Repository
@Slf4j
public class MemoryMemberRepository implements MemberRepository{

    private static Map<String, BeforeMember> repository = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(1);

    @Override
    public BeforeMember save(BeforeMember member) {
        member.setMemberNo(sequence.getAndIncrement());
        member.setGrade(MemberLevel.NORMAL);
        log.info("save member={}",member);
        return repository.put(member.memberId, member);

    }

    @Override
    public BeforeMember update(BeforeMember updateMember) {
        BeforeMember member = repository.get(updateMember.getMemberId());
        updateMember.setMemberNo(member.getMemberNo());
        repository.put(updateMember.getMemberId(), updateMember);
        log.info("update member={}", updateMember);
        return repository.get(updateMember.getMemberId());
    }

    @Override
    public String delete(String memberId) {
        BeforeMember deleteMember = repository.remove(memberId);
        log.info("delete member={}", deleteMember);
        return deleteMember.getMemberId();
    }

    @Override
    public Optional<BeforeMember> findById(String memberId) {
        Optional<BeforeMember> any = findAll().stream()
                .filter(m -> m.getMemberId().equals(memberId)).findAny();
        return any;
    }

    public List<BeforeMember> findAll() {
        return new ArrayList<>(repository.values());
    }



    //테스트용
    public void clearAll() {
        repository.clear();
    }

    //테스트용
    public void init() {
        BeforeMember memberA = new BeforeMember("memberA", "userA", "1234", MemberLevel.VIP);
        BeforeMember memberB = new BeforeMember("memberB", "userB", "1234", MemberLevel.VIP);
        this.save(memberA);
        this.save(memberB);
    }

    //테스트용
    public Long getMemberNo(String memberId) {
        BeforeMember member = repository.get(memberId);
        return member.getMemberNo();
    }
}
