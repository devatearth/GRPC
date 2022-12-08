import com.vinsguru.models.Money;
import io.grpc.stub.StreamObserver;

public class WithdrawResponseObserver implements StreamObserver<Money> {
    @Override
    public void onNext(Money money) {
        System.out.println(
                "Received :" + money.getValue()
        );
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(
                throwable.getMessage()
        );
    }

    @Override
    public void onCompleted() {
        System.out.println(
                "Server is Done"
        );
    }
}
