package spring.jwProject.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.jwProject.domain.address.Address;
import spring.jwProject.repository.Address.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    /**
     * 주소 저장
     */
    public Address save(Address address) {
        return repository.save(address);
    }
    // 주소 수정, 주소 삭제, 주소 변경
}
