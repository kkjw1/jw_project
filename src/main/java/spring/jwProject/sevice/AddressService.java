package spring.jwProject.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.jwProject.domain.address.Address;
import spring.jwProject.repository.address.AddressRepository;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.validation.form.ManageAddress;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    /**
     * 회원가입시 주소 추가
     * @param address
     * @return 성공:address,실패:exception
     */
    public Address signUp(Address address) {
        address.updateMain(true);
        return addressRepository.save(address);
    }


    /**
     * 주소 추가 test용
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
        return addressRepository.save(address);
    }

    /**
     * 주소 추가
     * @param manageAddress
     * @return 성공:address,실패:exception
     */
    public Address save(ManageAddress manageAddress) {
        Address address = new Address(
                memberRepository.findById(manageAddress.getMemberId()),
                manageAddress.getAddressName(),
                manageAddress.getRecipientName(),
                manageAddress.getPhoneNumber(),
                manageAddress.getPostcode(),
                manageAddress.getRoadAddress(),
                manageAddress.getDetailAddress(),
                manageAddress.getDeliveryRequest(),
                manageAddress.getMainAddress());

        if (address.getAddressName().isEmpty()) {
            address.updateAddressName(address.getRoadAddress());
        }
        if (address.getMainAddress() == true) {
            //이전의 기본배송지를 일반 배송지로 변경
            Address mainAddress = addressRepository.findMainAddress(manageAddress.getMemberId());
            mainAddress.updateMain(false);
        }
        return addressRepository.save(address);
    }

    /**
     * 주소 리스트 출력
     * @param memberId
     * @return 성공:List<Address>, 실패:[]
     */
    public List<Address> getAddresses(String memberId) {
        return addressRepository.findAddresses(memberId);
    }
    // 주소 수정, 주소 삭제, 주소 변경

    /**
     * 주소 삭제
     * @param no
     * @return 성공:true, 실패:false
     */
    public boolean delete(Long no) {
        return addressRepository.delete(no);
    }


}
