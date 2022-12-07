package grpcbasics;

import com.vinsguru.models.Balance;
import com.vinsguru.models.BalanceCheckRequest;
import com.vinsguru.models.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        responseObserver.onNext(Balance.newBuilder()
                .setAmount(AccountDataBase.getBalance(request.getAccountNumber()))
                .build());
        responseObserver.onCompleted();
    }
}
