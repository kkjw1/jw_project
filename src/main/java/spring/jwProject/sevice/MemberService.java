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
import java.util.*;
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
    public Map<String, Object> memberModify(UpdateMember updateMember) {

        Member member1 = repository.findById(updateMember.getId());
        if (member1 == null) {
            return null;
        }
        UpdateMember beforeMember = memberToUpdateMember(member1);

        if (updateMember.getPassword().isEmpty()) {
            updateMember.setPassword(beforeMember.getPassword());
        }

        Member member2 = repository.update(updateMember);
        if (member2 == null) {
            return null;
        }
        UpdateMember afterMember = memberToUpdateMember(member2);


        log.info("beforeMember={}", beforeMember.getPassword());
        log.info("afterMember={}", afterMember.getPassword());


        Field[] UpdateMemberFields = beforeMember.getClass().getDeclaredFields();
        Map<String, Object> result = new HashMap<>();

        try {
            for (Field f : UpdateMemberFields) {
                f.setAccessible(true);
                String fieldName = f.getName();
                Object s1 = f.get(beforeMember);
                Object s2 = f.get(afterMember);

                if (fieldName.equals("checkPassword")) {
                    continue;
                }

                if (!Objects.equals(s1,s2)) {
                    result.put(f.getName(), s2);
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
