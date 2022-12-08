package grpcbasics;

import com.vinsguru.models.Balance;
import com.vinsguru.models.DepositRequest;
import io.grpc.stub.StreamObserver;

public class CashStreamingRequestObserver implements StreamObserver<DepositRequest> {

    private  StreamObserver<Balance> balanceStreamObserver;
    private int accountBalance;

    public CashStreamingRequestObserver(StreamObserver<Balance> balanceStreamObserver) {
        this.balanceStreamObserver = balanceStreamObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        int accountNumber = depositRequest.getAccountNumber();
        int depositRequestAmount = depositRequest.getAmount();
        this.accountBalance = AccountDataBase.addBalance(accountNumber, depositRequestAmount);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder().setAmount(this.accountBalance).build();
        this.balanceStreamObserver.onNext(balance);
        this.balanceStreamObserver.onCompleted();
    }
}
