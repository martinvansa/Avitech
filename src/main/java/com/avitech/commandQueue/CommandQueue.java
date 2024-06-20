package com.avitech.commandQueue;

import com.avitech.model.Command;

import java.util.LinkedList;
import java.util.Queue;

public class CommandQueue {

    private final Queue<Command> queue = new LinkedList<>();
    private final int capacity;

    public CommandQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(Command command) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }

        queue.add(command);
        notifyAll();
    }

    public synchronized Command consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        Command command = queue.poll();
        notifyAll();
        return command;
    }
}
