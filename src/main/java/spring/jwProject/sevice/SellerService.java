package spring.jwProject.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.jwProject.domain.member.Member;
import spring.jwProject.domain.seller.Seller;
import spring.jwProject.repository.seller.SellerRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class SellerService {

    public final SellerRepository repository;


    /**
     * 판매자 회원가입
     */
    public Seller signUp(Seller seller) {
        return repository.save(seller);
    }
}
