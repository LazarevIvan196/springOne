package org.example.services;

import org.example.dao.TaskDao;
import org.example.domain.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class TaskService {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> getAllTask(int offset, int limit) {
        return taskDao.getAllTask(offset, limit);
    }

    public int getAllCountTask() {
        return taskDao.getAllCountTask();
    }


    public Task getById(int id) {
       Task task = taskDao.getTaskById(id);
        if (isNull(task)) {
            throw new RuntimeException("Not found task for id" + id);
        }
        return task;
    }


    public void createTask(Task task) {
        taskDao.saveOrupdateTask(task);

    }

    @Transactional
    public void deleteTask(int id) {
        Task task = taskDao.getTaskById(id);
        if (isNull(task)) {
            throw new RuntimeException("Not found task");
        }
        taskDao.deleteTask(task);
    }


}
