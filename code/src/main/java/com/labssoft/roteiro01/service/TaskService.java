package com.labssoft.roteiro01.service;

import com.labssoft.roteiro01.entity.Task;
import com.labssoft.roteiro01.entity.dto.CreateTask;
import com.labssoft.roteiro01.entity.dto.UpdateTask;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Iterable<Task> listAll() {
        return taskRepository.findAll();
    }

    public Task create(CreateTask task) {
        Task newTask = new Task(task.getTitle(), task.getDescription());   
        return taskRepository.save(newTask);
    }

    public boolean delete(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Task complete(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (task.getStatus() != TaskStatus.Completed) {
                task.setStatus(TaskStatus.Completed);
                return taskRepository.save(task);
            }
        }
        return null;
    }

    public Task edit(Long id, UpdateTask updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task);
        }
        return null;
    }
}
