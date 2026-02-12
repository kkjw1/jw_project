package spring.jwProject.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.jwProject.domain.address.Address;
import spring.jwProject.repository.address.AddressRepository;

import java.util.List;

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
        if (address.getMainAddress() != true) {
            address.updateMain(false);
        }
        return repository.save(address);
    }

    /**
     * 주소 리스트 출력
     * @param memberId
     * @return 성공:List<Address>, 실패:[]
     */
    public List<Address> getAddresses(String memberId) {
        return repository.findAddresses(memberId);
    }
    // 주소 수정, 주소 삭제, 주소 변경
}
