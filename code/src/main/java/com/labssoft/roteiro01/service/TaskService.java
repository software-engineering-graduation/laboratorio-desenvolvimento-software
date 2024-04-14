package com.labssoft.roteiro01.service;

import com.labssoft.roteiro01.entity.Task;
import com.labssoft.roteiro01.entity.dto.CreateTask;
import com.labssoft.roteiro01.entity.dto.UpdateTask;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;
import com.labssoft.roteiro01.exceptions.InvalidFieldFormatException;
import com.labssoft.roteiro01.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Iterable<Task> listAll() {
        return taskRepository.findAll();
    }

    public Task create(CreateTask task) throws InvalidFieldFormatException {
        validateTaskFields(task);
        Task newTask = createNewTask(task);
        return taskRepository.save(newTask);
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
        Calendar currentDate = truncateTime(Calendar.getInstance());
        Calendar dueDate = truncateTime(Calendar.getInstance());
        dueDate.setTime(task.getDueDate());
    
        if (dueDate.compareTo(currentDate) < 0) {
            throw new InvalidFieldFormatException("Data de vencimento deve ser maior ou igual que a data atual.");
        }
        task.setDueDays(null);
    }
    
    private Calendar truncateTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }    

    private void validateDueDays(CreateTask task) throws InvalidFieldFormatException {
        if (task.getDueDays() < 1) {
            throw new InvalidFieldFormatException("Dias para vencimento deve ser maior ou igual a 1.");
        }
        task.setDueDate(null);
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
