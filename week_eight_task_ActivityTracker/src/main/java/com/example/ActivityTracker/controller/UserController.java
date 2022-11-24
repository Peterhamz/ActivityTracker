package com.example.ActivityTracker.controller;

import com.example.ActivityTracker.Dto.TaskDto;
import com.example.ActivityTracker.Dto.UserLoginDto;
import com.example.ActivityTracker.Dto.UserSignUpDto;
import com.example.ActivityTracker.Dto.ViewTaskDto;
import com.example.ActivityTracker.Enum.TaskStatus;
import com.example.ActivityTracker.model.User;
import com.example.ActivityTracker.service.serviceImpl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }



    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody UserSignUpDto userSignUpDto){
        userService.registerUser(userSignUpDto);
        return  new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }



    @PostMapping( "/login")
    public String login(@RequestBody UserLoginDto userLoginDto){
        userService.loginUser(userLoginDto);

        return "login";
    }


    @PostMapping("/create-task")
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto, User user){
        userService.createTask(taskDto, user);

        return  new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }


    @GetMapping("/task/{taskId}")
    public ResponseEntity<ViewTaskDto> viewTask(@PathVariable Long taskId, User user){
        ViewTaskDto taskDto = userService.viewTask(taskId, user);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }


    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ViewTaskDto>> viewAllTask(@PathVariable Long userId, User user){
        List<ViewTaskDto> allTask = userService.viewAllTask(userId, user);
        return new ResponseEntity<>(allTask, HttpStatus.OK);
    }



    @GetMapping("/viewPending/{taskStatus}")
    public ResponseEntity<List<ViewTaskDto>> viewPendingTask(@PathVariable TaskStatus taskStatus, User user){
        List<ViewTaskDto> pendTask = userService.viewPendingTask(taskStatus, user);
        return new ResponseEntity<>(pendTask, HttpStatus.OK);
    }


    @GetMapping("/viewCompleted/{taskStatus}")
    public ResponseEntity<List<ViewTaskDto>> viewCompleteTask(@PathVariable TaskStatus taskStatus, User user){
        List<ViewTaskDto> completeTask = userService.viewCompletedTask(taskStatus, user);
        return new ResponseEntity<>(completeTask, HttpStatus.OK);
    }


    @GetMapping("/viewProgress/{taskStatus}")
    public ResponseEntity<List<ViewTaskDto>> viewProgressTask(@PathVariable TaskStatus taskStatus, User user){
        List<ViewTaskDto> inProgressTask = userService.viewInProgressTask(taskStatus, user);
        return new ResponseEntity<>(inProgressTask, HttpStatus.OK);
    }


    @GetMapping("/movePending/{taskId}")
    public ResponseEntity<String> movePending(@PathVariable Long taskId, User user){
                userService.moveToPending(taskId, user);
        return new ResponseEntity<>("Task has been moved", HttpStatus.OK);
    }



    @GetMapping("/moveCompleted/{taskId}")
    public ResponseEntity<String> moveDone(@PathVariable Long taskId, User user){
                 userService.moveToDone(taskId, user);
        return new ResponseEntity<>("Task has been moved", HttpStatus.OK);
    }


    @GetMapping("/edit/{taskId}")
    public ResponseEntity<String> editTask(@PathVariable Long taskId,TaskDto taskDto, User user){
        userService.editTask(taskId, taskDto, user);
        return new ResponseEntity<>("Task has been edited", HttpStatus.OK);
    }



    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        userService.deleteTask(taskId);
        return new ResponseEntity<>("task deleted successfully", HttpStatus.NO_CONTENT);
    }


}
