package berlin.myboard.web3j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class Web3jInvestmentService {

    private final Web3j web3j;
    private final String contractAddress;
    private final Credentials credentials;
    private final ContractGasProvider gasProvider;

    private final String WALLET_ADDRESS;
    private final String CONTRACT_ADDRESS;
    private final String PRIVATE_KEY;
    private final String INFURA_API_URL;

    @Autowired
    public Web3jInvestmentService(
            @Value("${metamask.WALLET_ADDRESS}") String walletAddress,
            @Value("${metamask.CONTRACT_ADDRESS}") String contractAddress,
            @Value("${metamask.PRIVATE_KEY}") String privateKey,
            @Value("${infura.API_URL}") String infuraApiUrl
    ) {
        this.WALLET_ADDRESS = walletAddress;
        this.CONTRACT_ADDRESS = contractAddress;
        this.PRIVATE_KEY = privateKey;
        this.INFURA_API_URL = infuraApiUrl;

        web3j = Web3j.build(new HttpService(INFURA_API_URL));
        this.contractAddress = CONTRACT_ADDRESS;
        credentials = Credentials.create(PRIVATE_KEY);
        gasProvider = new DefaultGasProvider();
    }

    //예치금 저장 함수
    public String saveDepositToContract(String investor, BigInteger amount) throws Exception {
        // 데이터 저장 함수 호출을 위한 트랜잭션 생성
        Function function = new Function(
                "addInvestorDeposit",
                List.of(new Utf8String(investor), new Uint(amount)),
                Collections.emptyList()
        );
        String encodedFunction = FunctionEncoder.encode(function);

        String gweiValue = "20";
        BigInteger gweiInBigInteger = new BigDecimal(gweiValue)
                .multiply(new BigDecimal("1000000000"))
                .toBigInteger();

        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, 5, 100);
        EthSendTransaction ethSendTransaction = transactionManager.sendTransaction(
                gweiInBigInteger, BigInteger.valueOf(210000),
                CONTRACT_ADDRESS, encodedFunction, BigInteger.ZERO
        );


        if (ethSendTransaction.hasError()) {
            throw new RuntimeException("Failed to save data to the contract: " + ethSendTransaction.getError().getMessage());
        }

        String transactionHash = ethSendTransaction.getTransactionHash();

        System.out.println("Transaction Hash: " + transactionHash);
        TransactionReceipt transactionReceipt = waitForTransactionReceipt(transactionHash);

        // 트랜잭션 결과 확인
        if (transactionReceipt == null) {
            throw new RuntimeException("Failed to save data to the contract.");
        }

        return "success";

    }

    //트랜잭션 올라갈 때까지 대기
    private TransactionReceipt waitForTransactionReceipt(String transactionHash) throws InterruptedException, IOException {
        int attempts = 0;
        int sleepDuration = 5000;
        while (attempts < 10) {
            EthGetTransactionReceipt ethGetReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
            if (ethGetReceipt.hasError()) {
                throw new RuntimeException("Error occurred while getting transaction receipt: " + ethGetReceipt.getError().getMessage());
            }
            TransactionReceipt transactionReceipt = ethGetReceipt.getResult();
            if (transactionReceipt != null) {
                return transactionReceipt;
            }
            attempts++;
            Thread.sleep(sleepDuration);
        }
        return null;
    }


    //예치금 불러오기
    public Long getInvestorDeposit(String investorName) throws Exception {
        Function function = new Function(
                "getInvestorDeposit",
                List.of(new Utf8String(investorName)),
                List.of(new TypeReference<Uint256>() {
                })
        );
        String encodedFunction = FunctionEncoder.encode(function);

        Transaction ethCallTransaction = Transaction.createEthCallTransaction(
                WALLET_ADDRESS,
                CONTRACT_ADDRESS,
                encodedFunction
        );
        EthCall response = web3j.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).send();

        List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        if (!result.isEmpty()) {
            Uint256 depositAmount = (Uint256) result.get(0);
            BigInteger amountBigInt = depositAmount.getValue();
            return amountBigInt.longValue();
        }

        return 0L;
    }

    //투자 함수(투자내역 저장, 예치금 저장)
    public String saveDataToContract(String investor, String companyName, BigInteger amount) throws Exception {
        // 데이터 저장 함수 호출을 위한 트랜잭션 생성
        Function function = new Function(
                "addInvestment",
                List.of(new Utf8String(investor),new Utf8String(companyName), new Uint(amount)),
                Collections.emptyList()
        );
        String encodedFunction = FunctionEncoder.encode(function);

        String gweiValue = "20";
        BigInteger gweiInBigInteger = new BigDecimal(gweiValue)
                .multiply(new BigDecimal("1000000000"))
                .toBigInteger();

        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, 3, 100);
        EthSendTransaction ethSendTransaction = transactionManager.sendTransaction(
                gweiInBigInteger, BigInteger.valueOf(1000000),
                CONTRACT_ADDRESS, encodedFunction, BigInteger.ZERO
        );


        if (ethSendTransaction.hasError()) {
            throw new RuntimeException("Failed to save data to the contract: " + ethSendTransaction.getError().getMessage());
        }

        String transactionHash = ethSendTransaction.getTransactionHash();

        System.out.println("Transaction Hash: " + transactionHash);
        TransactionReceipt transactionReceipt = waitForTransactionReceipt(transactionHash);

        // 트랜잭션 결과 확인
        if (transactionReceipt == null) {
            throw new RuntimeException("Failed to save data to the contract.");
        }

        return "success";

    }



}
