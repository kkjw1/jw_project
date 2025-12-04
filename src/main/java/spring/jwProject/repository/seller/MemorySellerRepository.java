package spring.jwProject.repository.seller;

import org.springframework.stereotype.Repository;
import spring.jwProject.domain.member.Member;
import spring.jwProject.domain.seller.Seller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class MemorySellerRepository implements SellerRepository{

    private Map<Long, Seller> repository = new ConcurrentHashMap<>();

    @Override
    public Seller save(Seller seller) {

        repository.put(seller.getSellerNo(), seller);

        return seller;
    }

}
