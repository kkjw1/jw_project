package spring.jwProject.repository.member;

import spring.jwProject.domain.BeforeMember;

import java.util.Optional;

public interface MemberRepository {

    /**
     * 회원 저장
     * @param member
     * @return 저장한 회원 정보
     */
    BeforeMember save(BeforeMember member);

    /**
     * 회원정보 수정
     * @param member
     * @return 수정한 회원 정보
     */
    BeforeMember update(BeforeMember member);


    /**
     * 회원 삭제
     * @param memberId
     * @return 삭제된 회원의 id
     */
    String delete(String memberId);

    /**
     * 회원 조회
     * @param memberId
     * @return 회원 아이디
     */
    Optional<BeforeMember> findById(String memberId);
}
