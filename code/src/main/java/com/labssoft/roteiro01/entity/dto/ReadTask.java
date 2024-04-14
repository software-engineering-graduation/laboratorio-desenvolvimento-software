package com.labssoft.roteiro01.entity.dto;

import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ReadTask {
    private String title;
    private String description;
    private TaskType type;
    private TaskStatus status;
    private String statusDescription;
    private TaskPriority priority;
}
