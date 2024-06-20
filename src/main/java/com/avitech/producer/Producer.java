package com.avitech.producer;

import com.avitech.commandQueue.CommandQueue;
import com.avitech.model.Command;

import java.util.List;

public class Producer implements Runnable{

    private final CommandQueue commandQueue;

    public Producer(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {

        List<Command> commands = List.of(
                new Command("Add",1),
                new Command("Add",2),
                new Command("PrintAll",1),
                new Command("DeleteAll",1),
                new Command("PrintAll",2));
        try {
            for (Command com : commands ){
                commandQueue.produce(com);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer was interrupted.");
        }
    }
}
