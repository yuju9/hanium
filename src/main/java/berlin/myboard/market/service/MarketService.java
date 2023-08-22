package berlin.myboard.market.service;

import berlin.myboard.deposit.domain.MemberDepositRes;
import berlin.myboard.domain.Member;
import berlin.myboard.market.Repository.MarketRepository;
import berlin.myboard.market.domain.Market;
import berlin.myboard.repository.MemberRepository;
import berlin.myboard.web3j.Web3jInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class MarketService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    private Web3jInvestmentService web3jInvestmentService;

    @Transactional
    public String payMarket(String userEmail, Long id) throws Exception {
        Market market = marketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("실패"));
        Optional<Member> memberOptional = memberRepository.findByUsername(userEmail);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (market.getPrice() > member.getDeposit()) {
                System.out.println("예치금 잔액 부족");
            }
            Long setDeposit = member.getDeposit() - market.getPrice();
            member.setDeposit(setDeposit);
            web3jInvestmentService.saveDataToContract(userEmail, market.getItemName(), BigInteger.valueOf(setDeposit));
            return "Success";
        } else {
            throw new IllegalArgumentException("Member not found with username: " + userEmail);
        }


    }

    @Transactional(readOnly = true)
    public Market findById(long id) {

        return marketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패"));
    }
}
