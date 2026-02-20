package spring.jwProject.repository.seller;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.seller.Seller;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaSellerRepository implements SellerRepository {

    private final EntityManager em;

    @Override
    public Seller save(Seller seller) {
        seller.setCreatedBy(seller.getName());
        seller.setCreatedDate(LocalDateTime.now());
        em.persist(seller);
        return seller;
    }

    @Override
    public Seller findById(String sellerId) {

        List<Seller> seller = em.createQuery("select s from Seller s where s.id =:sellerId", Seller.class)
                .setParameter("sellerId", sellerId)
                .getResultList();
        if (seller.isEmpty()) {
            return null;
        }
        return seller.get(0);
    }
}
