package grpcbasics;

import com.vinsguru.models.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        responseObserver.onNext(Balance.newBuilder()
                .setAmount(AccountDataBase.getBalance(request.getAccountNumber()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDataBase.getBalance(accountNumber);
        if(balance<amount){
            Status status = Status.FAILED_PRECONDITION.withDescription("No enough money.");
            responseObserver.onError(status.asRuntimeException());
            return;
        }
        for (int i =0;i<(amount/10);i++){
            Money money = Money.newBuilder().setValue(10).build();
            responseObserver.onNext(money);
            AccountDataBase.deductBalance(accountNumber,10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequestObserver(responseObserver);
    }
}
