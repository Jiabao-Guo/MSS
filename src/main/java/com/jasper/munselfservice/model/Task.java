package com.jasper.munselfservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Task {
    String uuid;
    String name;
    String description;
    Integer progress;
    Integer total;
    Integer status;

    public static final Integer STATUS_RUNNING = 0;
    public static final Integer STATUS_SUCCESS = 1;
    public static final Integer STATUS_FAILED = 2;

    public String toRedisString() {
        return String.format("%s,%s,%s,%d,%d,%d", getUuid(), getName(), getDescription(), getProgress(), getTotal(), getStatus());
    }

    public static Task fromRedisString(String taskString) throws IndexOutOfBoundsException {
        String[] parts = taskString.split(",");
        return new Task(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
    }
}
