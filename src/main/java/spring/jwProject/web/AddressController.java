package spring.jwProject.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.jwProject.domain.address.Address;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.address.AddressRepository;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.sevice.AddressService;
import spring.jwProject.validation.form.LoginMember;
import spring.jwProject.validation.form.ManageAddress;
import spring.jwProject.validation.form.UpdateAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/member/mypage/addressManage")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    //배송지 관리 페이지, /member/mypage/addressManage?memberId=test
    @GetMapping("")
    public String addressForm(@RequestParam("memberId") String memberId, HttpServletRequest request, Model model) {
        if (!new LoginMember().loginCheck(request, model)) {
            return "redirect:/member/login?redirectURL=" + request.getRequestURI();
        }
        //주소 리스트 출력 기능
        List<Address> addresses = addressService.getAddresses(memberId);
        if (addresses.isEmpty()) {
            model.addAttribute("addresses", Collections.emptyList());
        } else if (addresses.get(0).getMainAddress() != true) {
            model.addAttribute("addresses", addresses);
        } else {
            Address mainAddress = addresses.get(0);
            List<Address> addressesList = addresses.subList(1, addresses.size());
            model.addAttribute("mainAddress", mainAddress);
            model.addAttribute("addresses", addressesList);
        }
        return "member/address_manage";
    }


    //배송지 추가 페이지
    @GetMapping("/add")
    public String addressAddForm(HttpServletRequest request, Model model) {
        if (!new LoginMember().loginCheck(request, model)) {
            return "redirect:/member/login?redirectURL=" + request.getRequestURI();
        }
        model.addAttribute("manageAddress", new ManageAddress());
        return "member/address_manage_add";
    }


    //배송지 추가
    @PostMapping("/add")
    public String addressAdd(@Validated @ModelAttribute("manageAddress") ManageAddress manageAddress, BindingResult bindingResult,
                             @RequestParam("memberId") String memberId, RedirectAttributes redirectAttributes,
                             HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            new LoginMember().loginCheck(request, model);
            return "member/address_manage_add";
        }

        manageAddress.setMemberId(memberId);
        log.info("manageAddress.mainAddress={}", manageAddress.getMainAddress());
        addressService.save(manageAddress);

        redirectAttributes.addAttribute("memberId", memberId);
        return "redirect:/member/mypage/addressManage";
    }

    //내정보 가져오기(배송지 추가 페이지)
    @GetMapping("/add/getMyInfo")
    @ResponseBody
    public List<String> getMyInfo(HttpServletRequest request) {
        LoginMember loginMember = (LoginMember)request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        log.info("getMyInfo id={}", loginMember.getId());
        Member member = memberRepository.findById(loginMember.getId());
        List<String> list = new ArrayList<>();
        list.add(member.getName());
        list.add(member.getPhoneNumber());
        return list;
    }

    //배송지 삭제
    @PostMapping("/delete")
    public String addressDelete(@RequestParam("addressNo") Long addressNo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (addressService.delete(addressNo)) {
            log.info("delete addressNo={}", addressNo);
        }
        LoginMember loginMember = (LoginMember) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        redirectAttributes.addAttribute("memberId", loginMember.getId());
        return "redirect:/member/mypage/addressManage";
    }

    //배송지 수정 폼 /member/mypage/addressManage/update?addressNo=
    @GetMapping("/update")
    public String addressUpdateForm(@RequestParam("addressNo") Long addressNo, Model model, HttpServletRequest request) {

        if (!new LoginMember().loginCheck(request, model)) {
            return "redirect:/member/login?redirectURL=" + request.getRequestURI();
        }

        Address address = addressRepository.findByNo(addressNo);
        UpdateAddress updateAddress = new UpdateAddress(
                address.getAddressName(),
                address.getRecipientName(),
                address.getPhoneNumber(),
                address.getPostcode(),
                address.getRoadAddress(),
                address.getDetailAddress(),
                address.getDeliveryRequest());

        updateAddress.setAddressNo(addressNo);
        model.addAttribute("updateAddress", updateAddress);
        return "member/address_manage_update";
    }

    //배송지 수정
    @PostMapping("/update")
    public String addressUpdate(@Validated @ModelAttribute("updateAddress") UpdateAddress updateAddress, BindingResult bindingResult,
                                @RequestParam("addressNo") Long addressNo, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {

        if(bindingResult.hasErrors()) {
            log.info("Address Update Error no={}", addressNo);
            new LoginMember().loginCheck(request, model);
            return "member/address_manage_update";
        }

        updateAddress.setAddressNo(addressNo);
        addressService.addressModify(updateAddress);

        LoginMember loginMember = (LoginMember) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        redirectAttributes.addAttribute("memberId", loginMember.getId());
        return "redirect:/member/mypage/addressManage";
    }

}
