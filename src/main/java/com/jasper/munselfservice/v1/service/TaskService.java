package com.jasper.munselfservice.v1.service;

import com.jasper.munselfservice.v1.helper.RedisHelper;
import com.jasper.munselfservice.v1.model.Task;
import jakarta.annotation.Nullable;

import java.util.UUID;
import java.util.logging.Logger;

public class TaskService {
    private final RedisHelper<String, String> redis;

    public TaskService(RedisHelper<String, String> redis) {
        this.redis = redis;
    }

    /**
     * Create a task, save to redis and return the uuid.
     *
     * @param taskName -
     * @param total    -
     * @return uuid
     */
    public Task createTask(String taskName, String description, int total) {
        String uuid = UUID.randomUUID().toString();
        Task task = new Task(uuid, taskName, description, 0, total, Task.STATUS_RUNNING);

        Logger.getLogger("TaskService").info(String.format(
            "Task %s created (%d/%d).", task.getName(), task.getProgress(), task.getTotal()));

        redis.set(uuid, task.toRedisString());
        return task;
    }

    /**
     * Get task by uuid.
     *
     * @param uuid -
     * @return task. null if not found.
     */
    public @Nullable Task getTask(String uuid) {
        String taskString = redis.get(uuid);
        try {
            return Task.fromRedisString(taskString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Update progress of a task.
     *
     * @param uuid        -
     * @param progress    New progress. Null if not changed.
     * @param description New description. Null if not changed.
     * @param status      New status. Null if not changed.
     * @return true if updated, false if not found.
     */
    public boolean updateTask(String uuid, String description, Integer progress, Integer status) {
        Task task = getTask(uuid);
        if (task == null) {
            return false;
        }

        if (progress != null) {
            if (progress >= task.getTotal()) {
                Logger.getLogger("TaskService").info(String.format(
                    "Task %s finished (%d/%d).", task.getName(), progress, task.getTotal()));
            }
            task.setProgress(progress);
        }

        if (description != null) {
            task.setDescription(description);
        }

        if (status != null) {
            task.setStatus(status);
        }

        redis.set(uuid, task.toRedisString());
        return true;
    }

    /**
     * Delete a task.
     *
     * @param uuid -
     * @return true if deleted, false if not found.
     */
    public boolean deleteTask(String uuid) {
        if (!redis.hasKey(uuid)) {
            return false;
        }

        Logger.getLogger("TaskService").info(String.format(
            "Task %s deleted.", uuid));
        redis.delete(uuid);
        return true;
    }

    /**
     * Get progress of a task.
     *
     * @param uuid -
     * @return progress. null if not found.
     */
    public Integer getProgress(String uuid) {
        Task task = getTask(uuid);
        if (task == null) {
            return null;
        }
        return task.getProgress();
    }
}
