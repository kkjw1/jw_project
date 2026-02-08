package spring.jwProject.sevice;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.jwProject.domain.BeforeMember;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.validation.form.UpdateMember;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final EntityManager em;

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
     * @return 성공:Map<필드명, 변경값>, 변경 X일 시 변경값은 공백(""),  실패:null
     */
    public Map<String, String> memberModify(UpdateMember updateMember) {

        Member member1 = repository.findById(updateMember.getId());
        if (member1 == null) {
            return null;
        }
        UpdateMember beforeMember = memberToUpdateMember(member1);


        Member member2 = repository.update(updateMember);
        if (member2 == null) {
            return null;
        }
        UpdateMember afterMember = memberToUpdateMember(member2);


        log.info("beforeMember={}", beforeMember.getName());
        log.info("afterMember={}", afterMember.getName());


        Field[] UpdateMemberFields = beforeMember.getClass().getDeclaredFields();
        Map<String, String> result = new HashMap<>();

        try {
            for (Field f : UpdateMemberFields) {
                f.setAccessible(true);
                if (f.get(beforeMember) != f.get(afterMember)) {
                    result.put(f.getName(), (String) f.get(afterMember));
                } else {
                    result.put(f.getName(), "");
                }
            }
        } catch (Exception e) {
            return null;
        }
        log.info("update result={}",result);
        return result;
    }

    public UpdateMember memberToUpdateMember(Member member) {
        return new UpdateMember(member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getTelecom(),
                member.getPhoneNumber(),
                member.getGender());
    }

    /**
     * 회원 탈퇴
     * @param memberId
     */
    public void withdraw(String memberId) {

    }

}
