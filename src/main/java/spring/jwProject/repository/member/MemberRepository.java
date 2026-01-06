package spring.jwProject.repository.member;


import spring.jwProject.domain.member.Member;

public interface MemberRepository {
    /**
     * 회원 저장
     * @param member
     * @return 등록한 회원
     */
    Member save(Member member);

    /**
     * 회원 검색
     * @return 중복X = true
     */
    Member findById(String id);
}
