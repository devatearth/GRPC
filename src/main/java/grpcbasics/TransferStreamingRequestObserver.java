package grpcbasics;

import com.vinsguru.models.Account;
import com.vinsguru.models.TransferRequest;
import com.vinsguru.models.TransferResponse;
import com.vinsguru.models.TransferStatus;
import io.grpc.stub.StreamObserver;

public class TransferStreamingRequestObserver implements StreamObserver<TransferRequest> {

    private StreamObserver<TransferResponse> responseStreamObserver;

    public TransferStreamingRequestObserver(StreamObserver<TransferResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        int fromAccount = transferRequest.getFromAccount();
        int toAccount = transferRequest.getToAccount();
        int amount = transferRequest.getAmount();
        int balance = AccountDataBase.getBalance(fromAccount);
        TransferStatus transferStatus = TransferStatus.FAILED;
        if(balance>=amount && fromAccount != toAccount){
            AccountDataBase.deductBalance(fromAccount,amount);
            AccountDataBase.addBalance(toAccount,amount);
            transferStatus = TransferStatus.SUCCESS;
        }
        Account account1 = Account.newBuilder().setAccountNumber(fromAccount).setAmount(AccountDataBase.getBalance(fromAccount)).build();
        Account account2 = Account.newBuilder().setAccountNumber(toAccount).setAmount(AccountDataBase.getBalance(toAccount)).build();
        TransferResponse transferResponse = TransferResponse.newBuilder().setStatus(transferStatus).addAccounts(account1).addAccounts(account2).build();
        responseStreamObserver.onNext(transferResponse);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        AccountDataBase.printAccountDetails();
        responseStreamObserver.onCompleted();
    }
}
