package spring.jwProject.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.jwProject.domain.address.Address;
import spring.jwProject.repository.address.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    /**
     * 회원가입시 주소 추가
     * @param address
     * @return 성공:address,실패:exception
     */
    public Address signUp(Address address) {
        address.updateMain(true);
        return repository.save(address);
    }

    /**
     * 주소 추가
     * @param address
     * @return 성공:address,실패:exception
     */
    public Address save(Address address) {
        if (address.getAddressName().isEmpty()) {
            address.updateAddressName(address.getRoadAddress());
        }
        address.updateMain(false);
        return repository.save(address);
    }
    // 주소 수정, 주소 삭제, 주소 변경
}
