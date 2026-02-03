package spring.jwProject.repository.Address;

import spring.jwProject.domain.address.Address;

public interface AddressRepository {
    /**
     * 주소 저장
     * @param address
     * @return
     */
    Address save(Address address);
}
