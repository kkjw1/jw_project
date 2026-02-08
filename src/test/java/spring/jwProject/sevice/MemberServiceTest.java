package spring.jwProject.sevice;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.validation.form.UpdateMember;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
@Transactional
class MemberServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberService service;
    @Autowired
    MemberRepository repository;

    @Test
    @DisplayName("로그인테스트")
    public void loginTest() throws Exception {
        //given
        Member member1 = new Member("member1", "member1@Email.com", "password", "name", Gender.MAN, "SKT",
                "010-1234-1234");
        Member member2 = new Member("member2", "member2@Email.com", "password", "name", Gender.WOMAN, "KT",
                "010-1234-1234");
        em.persist(member1);
        em.persist(member2);
        em.flush();
        em.clear();

        log.info("로그인 성공");
        String id1 = "member1";
        String password1 = "password";
        Member login = service.login(id1, password1);
        Assertions.assertThat(login.getNo()).isEqualTo(member1.getNo());

        log.info("로그인 실패, 아이디 불일치");
        String id2 = "member3";
        String password2 = "password";
        Member loginFail = service.login(id2, password2);
        Assertions.assertThat(loginFail).isNull();

        log.info("로그인 실패, 비밀번호 불일치");
        String id3 = "member2";
        String password3 = "password2";
        Member loginFail2 = service.login(id3, password3);
        Assertions.assertThat(loginFail2).isNull();
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    public void memberModifyTest() throws Exception {
        //given
        Member member1 = new Member("memberTest", "member1@Email.com", "password", "name", Gender.MAN, "SKT",
                "010-1234-1234");
        em.persist(member1);
        em.flush();
        em.clear();

        Member beforeMember = repository.findById(member1.getId());
        em.flush();
        em.clear();
        // 이름, 이메일 변경함
        Member afterMember = repository.update(new UpdateMember(
                member1.getId(), "test", "test","test", "SKT", "010-1234-1234", Gender.MAN));
        em.flush();
        em.clear();

        UpdateMember beforeUpdateMember = new UpdateMember(beforeMember.getId(),
                beforeMember.getName(),
                beforeMember.getEmail(),
                beforeMember.getPassword(),
                beforeMember.getTelecom(),
                beforeMember.getPhoneNumber(),
                beforeMember.getGender());
        UpdateMember afterUpdateMember = new UpdateMember(afterMember.getId(),
                afterMember.getName(),
                afterMember.getEmail(),
                afterMember.getPassword(),
                afterMember.getTelecom(),
                afterMember.getPhoneNumber(),
                afterMember.getGender());

        // return 형태: Map<필드명, [이전값, 이후값]>
        System.out.println("beforeUpdateMember = " + beforeUpdateMember.getName());
        System.out.println("afterUpdateMember = " + afterUpdateMember.getName());

        Field[] member = beforeUpdateMember.getClass().getDeclaredFields();
        Map<String, List<String>> result = new HashMap<>();

        for (Field f : member) {
            f.setAccessible(true);
            System.out.println(f.getName() + "= " + f.get(beforeUpdateMember) + ", " + f.get(afterUpdateMember));
            if (f.get(beforeUpdateMember) != f.get(afterUpdateMember)) {
                List<String> data = new ArrayList<>();
                data.add((String) f.get(beforeUpdateMember));
                data.add((String) f.get(afterUpdateMember));
                result.put(f.getName(), data);
            }
        }

//        System.out.println("result = " + result);

        for (String s : result.keySet()) {
            System.out.println("s = " + s);
            System.out.println("result.get(s) = " + result.get(s));
        }

/*

        Map<String, List<String>> result = new HashMap<>();
        for (Field field : test) {
            field.setAccessible(true); // private 필드 접근
            if (!field.get(t1).equals(field.get(t2))) {
                List<String> l1 = new ArrayList<>();
                l1.add((String) field.get(t1));
                l1.add((String) field.get(t2));
                result.put(field.getName(), l1);
            }
        }

        System.out.println(result);

        for (String s : result.keySet()) {
            System.out.println(s + ": " + result.get(s));
        }
*/


    }
}