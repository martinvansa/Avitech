package com.avitech.model;

import java.util.Objects;

public class Command {
    private final String command;
    private final int order;

    public Command(String command, int order) {
        this.command = command;
        this.order = order;
    }

    public String getCommand() {
        return command;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command1 = (Command) o;
        return getOrder() == command1.getOrder() && Objects.equals(getCommand(), command1.getCommand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommand(), getOrder());
    }
}
