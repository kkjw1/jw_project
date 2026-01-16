package spring.jwProject.sevice;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.jwProject.domain.BeforeMember;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.validation.form.UpdateMember;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;


    /**
     * 회원가입 O
     * @param member
     */
    public Member signUp(Member member) {
        return repository.save(member);
    }

    /**
     * 아이디 중복 검사 O
     * @return 중복아니면 true, 중복이면 false
     */
    public boolean checkId(String id) {
        return repository.findById(id) == null;
    }

    /**
     * 로그인 O
     * @param id
     * @param password
     * @return 성공:Member, 실패:null
     */
    public Member login(String id, String password) {
/*        return repository.findById(memberId)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);*/
        Member findMember = repository.findById(id);
        if (findMember != null && findMember.passwordEquals(password)) {
            return findMember;
        }
        return null;
    }

    /**
     * 회원 수정 O
     * @param updateMember
     */
    public Member memberModify(UpdateMember updateMember) {
        return repository.update(updateMember);
    }


    /**
     * 회원 탈퇴
     * @param memberId
     */
    public void withdraw(String memberId) {

    }

}
