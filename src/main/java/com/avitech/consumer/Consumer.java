package com.avitech.consumer;

import com.avitech.commandQueue.CommandQueue;
import com.avitech.exception.UserDataException;
import com.avitech.service.UserPersistenceService;

public class Consumer implements Runnable {
    private final CommandQueue commandQueue;
    private final UserPersistenceService ups;

    public Consumer(CommandQueue commandQueue, UserPersistenceService ups) {
        this.commandQueue = commandQueue;
        this.ups = ups;
    }

    @Override
    public void run() {
        try {
            ups.initDatabase(commandQueue);
        } catch (UserDataException e) {
            throw new RuntimeException(e);
        }
    }
}
