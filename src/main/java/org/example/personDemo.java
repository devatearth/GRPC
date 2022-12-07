package org.example;

import com.google.protobuf.Int32Value;
import com.vinsguru.models.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class personDemo {
    public static void main(String[] args) throws IOException {
        Person sam1 = Person.newBuilder()
                .setAge(Int32Value.newBuilder().setValue(25).build())
                .setName("sam").build();

        //Serialization
        Path path = Paths.get("sam.ser");
        Files.write(path,sam1.toByteArray());
        //Deserialization
        byte[] bytes = Files.readAllBytes(path);
        Person newPerson = Person.parseFrom(bytes);
        System.out.println(newPerson);
    }
}
