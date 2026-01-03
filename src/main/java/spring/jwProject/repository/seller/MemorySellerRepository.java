package spring.jwProject.repository.seller;

import org.springframework.stereotype.Repository;
import spring.jwProject.domain.BeforeMember;
import spring.jwProject.domain.seller.Seller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@Repository
public class MemorySellerRepository implements SellerRepository{

    private Map<Long, Seller> repository = new ConcurrentHashMap<>();

    @Override
    public Seller save(Seller seller) {

        return seller;
    }

}
