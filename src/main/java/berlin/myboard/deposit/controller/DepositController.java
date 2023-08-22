package berlin.myboard.deposit.controller;


import berlin.myboard.domain.Member;
import berlin.myboard.deposit.domain.*;
import berlin.myboard.deposit.service.PayService;
import berlin.myboard.web3j.Web3jInvestmentService;
import berlin.myboard.repository.MemberRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Optional;


@Tag(name="예치금 거래", description = "예치금 거래 api")
@Slf4j
@Controller
public class DepositController {

    @Autowired
    PayService payService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private Web3jInvestmentService web3jInvestmentService;

    private Pay resPay;


    @Operation(summary = "예치금 입금 폼", description = "파라미터")
    @GetMapping("/deposit")
    public String depositform(Model model, @AuthenticationPrincipal OAuth2User oauth2User) throws Exception {
        String email = oauth2User.getAttribute("email");
        System.out.println(email);
        Optional<Member> memberOptional = memberRepository.findByUsername(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            Long depositAmount = member.getDeposit();
            model.addAttribute("deposit", depositAmount);
        }
        Long bcdeposit = web3jInvestmentService.getInvestorDeposit(email);
        model.addAttribute("bcdeposit", bcdeposit);
        return "Deposit";
    }

    //결제를 하는 페이지
    @GetMapping("/depositPay")
    public String depositpay(Model model, Deposit deposit, @AuthenticationPrincipal OAuth2User oauth2User, Pay pay){
        String email = oauth2User.getAttribute("email");
        MemberDepositRes memberDepositRes = payService.findAll(email);
        model.addAttribute("member", memberDepositRes);
        model.addAttribute("deposit", deposit);
        model.addAttribute("pay", pay.getPGName());
        return "DepositPay";
    }

    //결제 완료 정보를 얻어올 엔드포인트
    @RequestMapping(value = {"/pay/complete"}, method = {RequestMethod.POST})
    @ResponseBody
    public String payComplete(PayRequest payRequest, @AuthenticationPrincipal OAuth2User oauth2User, Deposit deposit) throws Exception {
        String res = "no";
        String email = oauth2User.getAttribute("email");
        Optional<Member> memberOptional = memberRepository.findByUsername(email);

        if(payRequest.getImp_uid() != null && payRequest.getMerchant_uid() != null){
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                Long newDeposit = member.getDeposit() + deposit.getPrice();
                // 사용자의 예치금 정보 업데이트
                member.setDeposit(newDeposit);

                // 수정된 사용자 정보 저장
                memberRepository.save(member);
                System.out.println(member.getDeposit());
                web3jInvestmentService.saveDepositToContract(email, BigInteger.valueOf(deposit.getPrice()));

            }



            res = "yes";
        }
        return res;
    }

    //결제 완료 확인후 값을 db에 저장
    @GetMapping(value = "/payclear")
    public String payClear(){
        //db에 결제정보를 저장한다
        System.out.println("결제 정보 저장 완료");
        return "home";
    }




}
