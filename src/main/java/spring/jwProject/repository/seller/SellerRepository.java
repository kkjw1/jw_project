package spring.jwProject.repository.seller;

import spring.jwProject.domain.member.Member;
import spring.jwProject.domain.seller.Seller;

public interface SellerRepository {
    /**
     * 판매자 가입
     * @param member 회원
     * @return Seller
     */
    Seller save(Seller seller);


    //todo 물건등록, 수정, 삭제, 물건찾기(물건 번호, 이름), 물건 전체 찾기
}
