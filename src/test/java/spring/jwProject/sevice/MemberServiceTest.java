package spring.jwProject.sevice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import spring.jwProject.domain.member.Member;
import spring.jwProject.domain.member.MemberLevel;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.repository.member.MemoryMemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static spring.jwProject.domain.member.MemberLevel.*;


class MemberServiceTest {

    public MemoryMemberRepository repository = new MemoryMemberRepository();
    public MemberService service = new MemberService(repository);

/*    @BeforeEach
    @DisplayName("초기화")
    void init() {
        repository.init();
    }*/

    @AfterEach
    void end() {
        repository.clearAll();
    }

    @Test
    @DisplayName("회원가입 정상")
    void signUp() {
        Member member1 = new Member("test1", "test1", "test1", "test1", VIP);
        Member member2 = new Member("test2", "test2", "test2", "test2", VIP);
        Member signUpMember1 = service.signUp(member1);
        Member signUpMember2 = service.signUp(member2);

        assertThat(member1).isEqualTo(signUpMember1);
        assertThat(member2).isEqualTo(signUpMember2);
    }

    @Test
    @DisplayName("회원가입 오류-아이디 중복")
    void signUpError1() {
        Member member1 = new Member("test1", "test1", "test1", "test1", VIP);
        Member signUpMember1 = service.signUp(member1);
        Member signUpMember2 = service.signUp(member1);

        assertThat(signUpMember1).isEqualTo(member1);
        assertThat(signUpMember2).isNull();
    }


    @Test
    @DisplayName("회원 탈퇴-성공")
    void withdraw() {
        Member member1 = new Member("test1", "test1", "test1", "test1", VIP);
        service.signUp(member1);

        service.withdraw(member1.getMemberId());

        assertThat(repository.findById(member1.getMemberId())).isEmpty();
    }


    @Test
    @DisplayName("회원 수정")
    void updateMember() {
        Member member1 = new Member("test1", "test1", "test1", "test1", VIP);
        service.signUp(member1);
        Member updateMember = new Member("test1", "update", "update", "update", NORMAL);
        updateMember.setMemberNo(repository.getMemberNo(member1.getMemberId()));

        Member updatedMember = service.updateMember(updateMember);

        assertThat(updatedMember).isEqualTo(updateMember);
    }

    @Test
    @DisplayName("로그인-성공")
    void login() {
        Member member1 = new Member("test1", "test1", "test1", "test1", VIP);
        Member member2 = new Member("test2", "test2", "test2", "test2", VIP);
        service.signUp(member1);
        service.signUp(member2);

        Member loginMember = service.login("test1", "test1");
        assertThat(loginMember).isNotNull();
    }

    @Test
    @DisplayName("로그인-실패")
    void loginFail() {
        Member member1 = new Member("test1", "test1", "test1", "test1", VIP);
        Member member2 = new Member("test2", "test2", "test2", "test2", VIP);
        service.signUp(member1);
        service.signUp(member2);

        Member loginMember = service.login("test1", "test2");
        assertThat(loginMember).isNull();

    }

}