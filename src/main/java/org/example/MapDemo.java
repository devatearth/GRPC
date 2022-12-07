package org.example;

import com.vinsguru.models.Car;
import com.vinsguru.models.Dealer;

public class MapDemo {
    public static void main(String[] args) {
        Car city = Car.newBuilder().setBrand("Honda").setName("city").build();
        Car accord = Car.newBuilder().setBrand("Honda").setName("accord").build();
        Dealer dealer = Dealer.newBuilder().putModel(20056, city).putModel(20067, accord).build();
        System.out.println(dealer);
        System.out.println(dealer.getModelOrThrow(20056));
        System.out.println(dealer.getModelOrDefault(20057,accord));
        System.out.println(dealer.getModelMap());
    }
}
