package org.example;

import com.devsguru.json.JPerson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Int32Value;
import com.google.protobuf.InvalidProtocolBufferException;
import com.vinsguru.models.Person;

public class PerformanceTest {
    public static void main(String[] args) {
        JPerson jPerson = new JPerson();
        jPerson.setAge(10);
        jPerson.setName("bob");
        ObjectMapper mapper = new ObjectMapper();
        Runnable runnable1 = ()->{
            try{
                byte[] bytes = mapper.writeValueAsBytes(jPerson);
                JPerson newJPerson = mapper.readValue(bytes,JPerson.class);
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        Person person = Person.newBuilder().setName("bob").setAge(Int32Value.newBuilder().setValue(25).build()).build();
        Runnable runnable2 = ()->{
            try {
                byte[] bytes = person.toByteArray();
                Person newPerson = Person.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        };
        for (int i =0 ;i<5;i++){
            runPerformanceTest(runnable1,"JSON");
            runPerformanceTest(runnable2,"PROTO");
        }
    }

    private static void runPerformanceTest(Runnable runnable,String method){
        long time1 = System.currentTimeMillis();
        for(int i=0;i<5000000;i++){
            runnable.run();
        }
        long time2 = System.currentTimeMillis();
        System.out.println(method +" took " +(time2-time1)+ "- ms");
    }
}
