package com.example.ActivityTracker.service;

import com.example.ActivityTracker.Dto.TaskDto;
import com.example.ActivityTracker.Dto.UserLoginDto;
import com.example.ActivityTracker.Dto.UserSignUpDto;
import com.example.ActivityTracker.Dto.ViewTaskDto;
import com.example.ActivityTracker.Enum.TaskStatus;
import com.example.ActivityTracker.model.User;

import java.util.List;

public interface UserService {


    void registerUser(UserSignUpDto userSignUpDto);

    void loginUser(UserLoginDto userLoginDto);

    String createTask(TaskDto taskDto, User user);

    ViewTaskDto viewTask(Long taskId, User user);

    List<ViewTaskDto> viewAllTask(Long userId, User user);

    List<ViewTaskDto> viewPendingTask(TaskStatus status, User user);

    List<ViewTaskDto> viewCompletedTask(TaskStatus status, User user);

    List<ViewTaskDto> viewInProgressTask(TaskStatus status, User user);

    void moveToPending(Long taskId, User user);

    void moveToDone(Long taskId, User user);

    String editTask(Long taskId, TaskDto taskDto, User user);

     void deleteTask(Long taskId);


}
