package spring.jwProject.repository.address;

import spring.jwProject.domain.address.Address;

import java.util.List;

public interface AddressRepository {
    /**
     * 주소 저장
     * @param address
     * @return 성공:address, 실패:exception
     */
    Address save(Address address);

    /**
     * 주소 리스트 불러오기
     * @return 성공:List<address>, 실패:빈리스트
     */
    List<Address> findAddresses(String memberId);

    /**
     * 기본 배송지 찾기
     * @param memberId
     * @return 성공:Address, 실패:exception
     */
    Address findMainAddress(String memberId);

    /**
     * 주소 수정
     * @param address
     */
//    Address update(Address address);

    /**
     * 주소 삭제
     * @param no
     * @return 성공:true, 실패:false
     */
    boolean delete(Long no);
}
