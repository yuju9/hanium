package berlin.myboard.deposit.service;

import berlin.myboard.domain.Member;
import berlin.myboard.repository.MemberRepository;
import berlin.myboard.deposit.domain.MemberDepositRes;
import berlin.myboard.web3j.Web3jInvestmentService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Builder
public class PayService {

    @Autowired
    MemberRepository memberRepository;
//
//    @Autowired
//    MarketRepository marketRepository;

    @Autowired
    private Web3jInvestmentService web3jInvestmentService;

    //멤버 객체 정보 조회
    public MemberDepositRes findAll(String username) {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return new MemberDepositRes(member);
        } else {
            throw new IllegalArgumentException("Member not found with username: " + username);
        }
    }

//    @Transactional
//    public void payDeposit(String username, int id) throws Exception {
//        SiteUser siteUser = memberRepository.findByUsername(username);
//        Market market = marketRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("실패"));
//        if (market.getPrice() > siteUser.getDeposit()) {
//            System.out.println("예치금 잔액 부족");
//        }
//        int setDeposit = siteUser.getDeposit() - market.getPrice();
//        siteUser.setDeposit(setDeposit);
////        web3jInvestmentService.saveDataToContract(userId, "00회사", BigInteger.valueOf(market.getPrice()));
//
//    }
//
//    @Transactional(readOnly = true)
//    public Market findById(int id) {
//
//        return marketRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패"));
//    }


}