package com.board.config;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

public class ViewCountRedisHandler {

    @NotNull
    private final LocalDateTime time;
    private long count;

    public ViewCountRedisHandler(LocalDateTime time, long count) {
        if (time == null) {
            throw new RuntimeException();
        }
        this.time = time;
        this.count = count;
    }

    public LocalDateTime getTime() {
        return (time);
    }

    public long getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

}
