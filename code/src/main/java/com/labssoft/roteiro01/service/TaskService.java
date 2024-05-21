package com.labssoft.roteiro01.service;

import com.labssoft.roteiro01.entity.Task;
import com.labssoft.roteiro01.entity.dto.CreateTask;
import com.labssoft.roteiro01.entity.dto.ReadTask;
import com.labssoft.roteiro01.entity.dto.UpdateTask;
import com.labssoft.roteiro01.entity.mapper.TaskReadMapper;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;
import com.labssoft.roteiro01.exceptions.InvalidFieldFormatException;
import com.labssoft.roteiro01.repository.TaskRepository;
import com.labssoft.roteiro01.util.DateCompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<ReadTask> listAll() {
        return TaskReadMapper.mapToReadTaskList(taskRepository.findAll());
    }

    public ReadTask create(CreateTask task) throws InvalidFieldFormatException {
        validateTaskFields(task);
        Task newTask = createNewTask(task);
        return TaskReadMapper.mapToReadTask(taskRepository.save(newTask));
    }
    
    private void validateTaskFields(CreateTask task) throws InvalidFieldFormatException {
        if (task.getType() != null) {
            if (task.getType().equals(TaskType.Date)) {
                validateDueDate(task);
                return;
            } 
            if (task.getType().equals(TaskType.Period)) {
                validateDueDays(task);
            }
        }
    }
    
    private void validateDueDate(CreateTask task) throws InvalidFieldFormatException {
        if (!DateCompare.isAtLeastToday(task.getDueDate())) {
            throw new InvalidFieldFormatException("Data de vencimento deve ser maior ou igual que a data atual.");
        }
        task.setDueDays(null);
    }


    private void validateDueDays(CreateTask task) throws InvalidFieldFormatException {
        if (task.getDueDays() < 1) {
            throw new InvalidFieldFormatException("Dias para vencimento deve ser maior ou igual a 1.");
        }
        task.setDueDate(DateCompare.addDays(new Date(), task.getDueDays()));
    }
    
    private Task createNewTask(CreateTask task) {
        return new Task(
                task.getTitle(),
                task.getDescription(),
                TaskStatus.InProgress,
                task.getType(),
                task.getDueDate(),
                task.getDueDays(),
                task.getPriority()
        );
    }

    public boolean delete(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ReadTask complete(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (task.getStatus() != TaskStatus.Completed) {
                task.setStatus(TaskStatus.Completed);
                return TaskReadMapper.mapToReadTask(taskRepository.save(task));
            }
        }
        return null;
    }

    public ReadTask edit(Long id, UpdateTask updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setPriority(updatedTask.getPriority());
            return TaskReadMapper.mapToReadTask(taskRepository.save(task));
        }
        return null;
    }

    public ReadTask findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(TaskReadMapper::mapToReadTask).orElse(null);
    }
}
