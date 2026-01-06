package spring.jwProject.repository.seller;

import spring.jwProject.domain.BeforeMember;
import spring.jwProject.domain.seller.Seller;

public interface SellerRepository {
    /**
     * 판매자 가입
     * @param
     * @return Seller
     */
    Seller save(Seller seller);


    //todo 물건등록, 수정, 삭제, 물건찾기(물건 번호, 이름), 물건 전체 찾기
}
