package org.example;

import com.vinsguru.models.Credential;
import com.vinsguru.models.EmailCredential;
import com.vinsguru.models.PhoneCredential;

public class OneOfDemo {
    public static void main(String[] args) {
        EmailCredential emailCredential = EmailCredential.newBuilder().setEmail("test")
                .setPassword("123").build();
        PhoneCredential phoneCredential = PhoneCredential.newBuilder().setPhoneNumber(1234)
                .setCode(234).build();
        Credential credential = Credential.newBuilder()
                .setEmailMode(emailCredential)
                .setPhoneMode(phoneCredential)
                .build();
        login(credential);
    }
    private static void login(Credential credential){
        switch (credential.getModeCase()){
            case EMAILMODE:
                System.out.println(credential.getEmailMode());
                break;
            case PHONEMODE:
                System.out.println(credential.getPhoneMode());
                break;
        }
    }
}
