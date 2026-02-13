package spring.jwProject.repository.address;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.jwProject.domain.address.Address;
import spring.jwProject.validation.form.UpdateAddress;

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
        return em.createQuery("select a from Address a join fetch a.member where a.member.id =:memberId order by a.mainAddress desc", Address.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public Address findByNo(Long no) {
        return em.createQuery("select a from Address a where a.no=:no", Address.class)
                .setParameter("no", no)
                .getSingleResult();
    }

    @Override
    public List<Address> findMainAddress(String memberId) {
        return em.createQuery("select a from Address a join fetch a.member where a.mainAddress = true and a.member.id =:memberId", Address.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public Address update(UpdateAddress updateAddress) {
        Address findAddress = findByNo(updateAddress.getAddressNo());
        findAddress.updateAddressName(updateAddress.getAddressName());
        findAddress.updateRecipientName(updateAddress.getRecipientName());
        findAddress.updatePhoneNumber(updateAddress.getPhoneNumber());
        findAddress.updatePostcode(updateAddress.getPostcode());
        findAddress.updateRoadAddress(updateAddress.getRoadAddress());
        findAddress.updateDetailAddress(updateAddress.getDetailAddress());
        findAddress.updateDeliveryRequest(updateAddress.getDeliveryRequest());
        return findAddress;
    }

    @Override
    public boolean delete(Long no) {
        int result = em.createQuery("delete from Address a where a.no=:no")
                .setParameter("no", no)
                .executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }
}
