package spring.jwProject.web.seller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.jwProject.domain.seller.Seller;
import spring.jwProject.repository.seller.SellerRepository;
import spring.jwProject.sevice.SellerService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    public final SellerRepository repository;
    public SellerService service;

    @GetMapping("/signup")
    public String signUpForm() {
        return "seller/signup";
    }


    @PostMapping("/signup")
    public String signUp(@RequestParam("sellerNo") String sellerNo) {
        Seller seller = new Seller();
        seller.setSellerNo(Long.parseLong(sellerNo));
        Seller signUpSeller = service.signUp(seller);
        return "home/sellerhome";
    }
}
