package com.labssoft.roteiro01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labssoft.roteiro01.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}