package org.example;

import com.vinsguru.models.Person;

public class DefaultDemo {
    public static void main(String[] args) {
        Person person = Person.newBuilder().build();
        System.out.println(person.getAddress());
        System.out.println(person.hasAddress());
    }
}
