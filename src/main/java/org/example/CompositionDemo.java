package org.example;

import com.google.protobuf.Int32Value;
import com.vinsguru.models.Address;
import com.vinsguru.models.Car;
import com.vinsguru.models.Person;

public class CompositionDemo {
    public static void main(String[] args) {
        Address trk_street = Address.newBuilder().setPostBox(123)
                .setStreet("trk street").build();

        Car city = Car.newBuilder().setBrand("Honda").setName("city").build();
        Car accord = Car.newBuilder().setBrand("Honda").setName("accord").build();

        Person deva = Person.newBuilder().setAge(Int32Value.newBuilder().setValue(25).build())
                .setName("Deva").setAddress(trk_street).addCar(city).addCar(accord).build();
        System.out.println(deva);

    }
}
