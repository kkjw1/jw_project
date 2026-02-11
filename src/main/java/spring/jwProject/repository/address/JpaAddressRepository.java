package spring.jwProject.repository.address;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.address.Address;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaAddressRepository implements AddressRepository {

    private final EntityManager em;

    @Override
    public Address save(Address address) {
        address.setCreatedBy(address.getMember().getId());
        address.setCreatedDate(LocalDateTime.now());
        try {
            em.persist(address);
            return address;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Address> findAddresses(String memberId) {
        return em.createQuery("select a from Address a join fetch a.member where a.member.id =:memberId", Address.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
