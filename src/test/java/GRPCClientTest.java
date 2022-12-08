import com.google.common.util.concurrent.Uninterruptibles;
import com.vinsguru.models.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GRPCClientTest {
    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext().build();
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.bankServiceStub = BankServiceGrpc.newStub(channel);
    }

    @Test
    public void balanceTest(){
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder().setAccountNumber(8).build();
        Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
        System.out.println(balance);
    }

    @Test
    public void withdrawTest(){
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(7).setAmount(40).build();
        this.blockingStub.withdraw(withdrawRequest).forEachRemaining(money->System.out.println("Received: " + money));
    }

    @Test
    public void withdrawAsyncTest(){
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(9).setAmount(40).build();
        this.bankServiceStub.withdraw(withdrawRequest,new WithdrawResponseObserver());
        System.out.println("Non Blocking call made to the server");
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
    }
}
