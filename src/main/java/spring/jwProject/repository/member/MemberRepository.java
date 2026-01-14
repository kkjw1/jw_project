package spring.jwProject.repository.member;


import spring.jwProject.domain.member.Member;

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
}
