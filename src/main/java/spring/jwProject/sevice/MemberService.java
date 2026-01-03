package spring.jwProject.sevice;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.jwProject.domain.BeforeMember;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;


    /**
     * 회원가입
     * @param member
     */
    public BeforeMember signUp(BeforeMember member) {
        if (repository.findById(member.getMemberId()).orElse(null) == null) {
            repository.save(member);
            return member;
        }
        log.info("signUp Fail member={}",member);
        return null;
    }

    /**
     * 회원 탈퇴
     * @param memberId
     */
    public void withdraw(String memberId) {
        repository.delete(memberId);
    }


    /**
     * 회원 수정
     * @param member
     */
    public BeforeMember updateMember(BeforeMember member) {
        return repository.update(member);
    }


    /**
     * 로그인
     * @param memberId
     * @param password
     * @return 성공:Member, 실패:null
     */
    public BeforeMember login(String memberId, String password) {
        return repository.findById(memberId)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }
}
