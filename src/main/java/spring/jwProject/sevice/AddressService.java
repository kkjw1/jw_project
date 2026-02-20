package spring.jwProject.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.jwProject.domain.address.Address;
import spring.jwProject.repository.address.AddressRepository;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.validation.form.ManageAddress;
import spring.jwProject.validation.form.UpdateAddress;

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
        address.updateMainAddress(true);
        return addressRepository.save(address);
    }


    /**
     * 주소 추가 (더미 데이터용)
     * @param address
     * @return 성공:address,실패:exception
     */
    public Address save(Address address) {
        if (address.getAddressName().isEmpty()) {
            address.updateAddressName(address.getRoadAddress());
        }
        if (address.getMainAddress() != true) {
            address.updateMainAddress(false);
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
            List<Address> mainAddress = addressRepository.findMainAddress(manageAddress.getMemberId());
            //기본배송지가 없는 경우
            if (!mainAddress.isEmpty()) {
                mainAddress.get(0).updateMainAddress(false);
            }
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

    /**
     * 주소 삭제
     * @param no
     * @return 성공:true, 실패:false
     */
    public boolean delete(Long no) {
        return addressRepository.delete(no);
    }

    /**
     * 주소 변경
     * @param updateAddress
     * @return 성공:Address, 실패: exception
     */
    public Address addressModify(UpdateAddress updateAddress) {
        Address address = new Address();
        address.updateAddressName(updateAddress.getAddressName());
        address.updateRecipientName(updateAddress.getRecipientName());
        address.updatePhoneNumber(updateAddress.getPhoneNumber());
        address.updatePostcode(updateAddress.getPostcode());
        address.updateRoadAddress(updateAddress.getRoadAddress());
        address.updateDetailAddress(updateAddress.getDetailAddress());
        address.updateDeliveryRequest(updateAddress.getDeliveryRequest());
        return addressRepository.update(address, updateAddress.getAddressNo());
    }

    /**
     * 기본 배송지로 변경
     * @param addressNo, memberId
     * @return 성공:Address, 실패:exception
     */
    public Address mainUpdate(Long addressNo, String memberId) {
        List<Address> beforeMain = addressRepository.findMainAddress(memberId);

        Address afterMain = addressRepository.findByNo(addressNo);
        afterMain.updateMainAddress(true);

        if (!beforeMain.isEmpty()) {
            beforeMain.get(0).updateMainAddress(false);
            addressRepository.update(beforeMain.get(0), beforeMain.get(0).getNo());
        }

        return addressRepository.update(afterMain, addressNo);
    }

}
