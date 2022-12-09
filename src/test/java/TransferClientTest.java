import com.vinsguru.models.BankServiceGrpc;
import com.vinsguru.models.TransferRequest;
import com.vinsguru.models.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferClientTest {

    private TransferServiceGrpc.TransferServiceBlockingStub blockingStub ;
    private TransferServiceGrpc.TransferServiceStub transferServiceStub ;

    @BeforeAll
    public void setup(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext().build();
        this.blockingStub = TransferServiceGrpc.newBlockingStub(channel);
        this.transferServiceStub = TransferServiceGrpc.newStub(channel);
    }

    @Test
    public void transferTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        TransferStreamingResponseObserver transferStreamingResponseObserve = new TransferStreamingResponseObserver(countDownLatch);
        StreamObserver<TransferRequest> transferRequestStreamObserver = this.transferServiceStub.transfer(transferStreamingResponseObserve);

        for(int i = 0;i<100;i++){
            TransferRequest transferRequest = TransferRequest.newBuilder()
                    .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setAmount(ThreadLocalRandom.current().nextInt(1, 21))
                    .build();
            transferRequestStreamObserver.onNext(transferRequest);
        }
        transferRequestStreamObserver.onCompleted();
        countDownLatch.await();
    }

}
