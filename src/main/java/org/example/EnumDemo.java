package org.example;

import com.vinsguru.models.BodyStyle;
import com.vinsguru.models.Car;

public class EnumDemo {
    public static void main(String[] args) {
        Car city = Car.newBuilder().setBrand("Honda").setName("city").setBodyStyle(BodyStyle.COUPE).build();
        Car accord = Car.newBuilder().setBrand("Honda").setName("accord").build();
        System.out.println(city.getBodyStyle());
        System.out.println(accord.getBodyStyle());
    }
}
