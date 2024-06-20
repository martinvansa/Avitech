package com.avitech;


import com.avitech.commandQueue.CommandQueue;
import com.avitech.consumer.Consumer;
import com.avitech.producer.Producer;
import com.avitech.service.UserPersistenceService;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.util.Optional;

public class AvitechApplication {


    public static void main(String[] args) throws SQLException {
        CommandQueue commandQueue = new CommandQueue(5);

        Thread producerThread = new Thread(new Producer(commandQueue));
        Thread consumerThread = new Thread(new Consumer(commandQueue, new UserPersistenceService()));

        producerThread.start();
        consumerThread.start();

/*        Optional<String> optionalString = Optional.ofNullable("tralalal");
        //optionalString.ifPresent(value -> System.out.println("Value is present: " + value));
        String value = optionalString.orElse("Default Value");
        String value_2 = optionalString.orElseGet(() -> someMethodToGenerateDefaultValue());

        System.out.println("Value is present: " + value_2)*/;
    }

    private static String someMethodToGenerateDefaultValue() {
        return "Vansa";
    }


}