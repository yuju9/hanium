package berlin.myboard.market.controller;

import berlin.myboard.market.service.MarketService;
import berlin.myboard.repository.MemberRepository;
import berlin.myboard.web3j.Web3jInvestmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name="상품 투자", description = "상품 투자 api")
@Controller
@Slf4j
public class MarketController {

    @Autowired
    MarketService marketService;


    @Operation(summary = "상품 1개씩 조회", description = "파라미터")
    //투자 상품 조회 (1개씩)
    @GetMapping("/payform/{id}")
    public String findById(@PathVariable long id, Model model) {
        model.addAttribute("market", marketService.findById(id));
        return "PayForm";
    }


    @Operation(summary = "상품 투자", description = "파라미터")
    @PutMapping("/payform/pay/{id}")
    public ResponseEntity<String> payDeposit(@PathVariable("id") Long id, @AuthenticationPrincipal OAuth2User oauth2User) throws Exception {
        String email = oauth2User.getAttribute("email");
        String success = marketService.payMarket(email, id);
        if (success.equals("Success")) {

            return ResponseEntity.ok("투자가 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("투자에 실패하였습니다.");
        }
    }




}
