package spring.jwProject.repository.seller;

import spring.jwProject.domain.BeforeMember;
import spring.jwProject.domain.seller.Seller;

public interface SellerRepository {
    /**
     * 판매자 가입
     * @param seller
     * @return 성공:Seller, 실패:null
     */
    Seller save(Seller seller);

    //todo 물건등록, 수정, 삭제, 물건찾기(물건 번호, 이름), 물건 전체 찾기

    /**
     * 판매자 아이디로 찾기
     * @param sellerId
     * @return 성공:Seller, 실패:null
     */
    Seller findById(String sellerId);
}
