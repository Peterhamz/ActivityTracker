package com.example.ActivityTracker.Dto;

import com.example.ActivityTracker.Enum.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ViewTaskDto {

    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate createdAt;
    private LocalDate completedAt;

}
