package spring.jwProject.repository.member;


import spring.jwProject.domain.member.Member;
import spring.jwProject.validation.form.UpdateMember;

public interface MemberRepository {
    /**
     * 회원 저장
     * @param member
     * @return 성공:Member, 실패:exception
     */
    Member save(Member member);

    /**
     * 회원 검색
     * @return 성공:Member, 실패:null
     */
    Member findById(String id);

    /**
     * 회원 수정
     * @param updateMember
     * @return 성공:Member, 실패:null
     */
    Member update(UpdateMember updateMember);
}
