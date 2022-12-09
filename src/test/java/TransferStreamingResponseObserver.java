import com.vinsguru.models.TransferResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class TransferStreamingResponseObserver implements StreamObserver<TransferResponse> {

    private CountDownLatch latch;

    public TransferStreamingResponseObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(TransferResponse transferResponse) {
        System.out.println(transferResponse.getStatus());
        transferResponse.getAccountsList()
                .stream()
                .map(account->account.getAccountNumber() + " : "+account.getAmount())
                .forEach(System.out::println);
    }

    @Override
    public void onError(Throwable throwable) {
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("All Transfer's Completed");
        latch.countDown();
    }
}
