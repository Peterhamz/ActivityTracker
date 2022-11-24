package com.example.ActivityTracker.service.serviceImpl;

import com.example.ActivityTracker.Dto.TaskDto;
import com.example.ActivityTracker.Dto.UserLoginDto;
import com.example.ActivityTracker.Dto.UserSignUpDto;
import com.example.ActivityTracker.Dto.ViewTaskDto;
import com.example.ActivityTracker.Enum.TaskStatus;
import com.example.ActivityTracker.Exception.CustomAppException;
import com.example.ActivityTracker.model.Task;
import com.example.ActivityTracker.model.User;
import com.example.ActivityTracker.repository.TaskRepository;
import com.example.ActivityTracker.repository.UserRepository;
import com.example.ActivityTracker.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    final TaskRepository taskRepository;
    final HttpSession httpSession;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.httpSession = httpSession;
    }

    @Override
    public void registerUser(UserSignUpDto userSignUpDto) {
        User user = new User();
        user.setName(userSignUpDto.getName());
        user.setEmail(userSignUpDto.getEmail());
        user.setPassword(userSignUpDto.getPassword());

        userRepository.save(user);

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        User user =  userRepository.findByEmailAndPassword(userLoginDto.getEmail(),
                userLoginDto.getPassword());

        if(user.getId()==null){
            throw new CustomAppException("User details not correct");
        }

        httpSession.setAttribute("userId", user.getId());
        httpSession.setAttribute("user",user);

    }

    @Override
    public String createTask(TaskDto taskDto, User user) {
        Task task = new Task();

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDate.now());
        user=(User)httpSession.getAttribute("user");
        task.setUser(user);
        taskRepository.save(task);

        return "Task Created";
    }

    @Override
    public ViewTaskDto viewTask(Long taskId, User user) {
        user=(User)httpSession.getAttribute("user");

        List<Task> task = taskRepository.findByIdAndUser(taskId, user);
        ViewTaskDto viewTaskDto = new ViewTaskDto();

        if(task.isEmpty()){
            throw new CustomAppException("No task to view");
        }

        viewTaskDto.setTitle(task.get(0).getTitle());
        viewTaskDto.setDescription(task.get(0).getDescription());
        viewTaskDto.setStatus(task.get(0).getStatus());
        viewTaskDto.setCreatedAt(task.get(0).getCreatedAt());
        viewTaskDto.setCompletedAt(task.get(0).getCompletedAt());

        return viewTaskDto;
    }

    @Override
    public List<ViewTaskDto> viewAllTask(Long userId, User user) {
        user=(User)httpSession.getAttribute("user");

       List<Task> tasks = taskRepository.findByIdAndUser(userId, user);
       List<ViewTaskDto> views = new ArrayList<>();

        if(tasks.isEmpty()){
            throw new CustomAppException("No task to view");
        }

        for (int i = 0; i < tasks.size(); i++) {
            ViewTaskDto viewTaskDto = new ViewTaskDto();

            viewTaskDto.setTitle(tasks.get(i).getTitle());
            viewTaskDto.setDescription(tasks.get(i).getDescription());
            viewTaskDto.setStatus(tasks.get(i).getStatus());
            viewTaskDto.setCreatedAt(tasks.get(i).getCreatedAt());
            viewTaskDto.setCompletedAt(tasks.get(i).getCompletedAt());

            views.add(viewTaskDto);
        }

       return views;
    }

    @Override
    public List<ViewTaskDto> viewPendingTask(TaskStatus status, User user) {
        user=(User)httpSession.getAttribute("user");

        List<Task> tasks = taskRepository.findByStatusAndUser(status, user);
        List<ViewTaskDto> listOfPending = new ArrayList<>();

        if(tasks.isEmpty()){
            throw new CustomAppException("No task to view");
        }

        for (int i = 0; i < tasks.size(); i++) {
            ViewTaskDto viewTaskDto = new ViewTaskDto();

            viewTaskDto.setTitle(tasks.get(i).getTitle());
            viewTaskDto.setDescription(tasks.get(i).getDescription());
            viewTaskDto.setStatus(tasks.get(i).getStatus());
            viewTaskDto.setCreatedAt(tasks.get(i).getCreatedAt());
            viewTaskDto.setCompletedAt(tasks.get(i).getCompletedAt());

            listOfPending.add(viewTaskDto);

        }

        return listOfPending;
    }

    @Override
    public List<ViewTaskDto> viewCompletedTask(TaskStatus status, User user) {
        user=(User)httpSession.getAttribute("user");

       List<Task> tasks = taskRepository.findByStatusAndUser(status, user);
       List<ViewTaskDto> listOfCompleted = new ArrayList<>();

        if(tasks.isEmpty()){
            throw new CustomAppException("No task to view");
        }

        for (int i = 0; i < tasks.size(); i++) {
            ViewTaskDto viewTaskDto = new ViewTaskDto();

            viewTaskDto.setTitle(tasks.get(i).getTitle());
            viewTaskDto.setDescription(tasks.get(i).getDescription());
            viewTaskDto.setStatus(tasks.get(i).getStatus());
            viewTaskDto.setCreatedAt(tasks.get(i).getCreatedAt());
            viewTaskDto.setCompletedAt(tasks.get(i).getCompletedAt());

            listOfCompleted.add(viewTaskDto);

        }


        return listOfCompleted;
    }

    @Override
    public List<ViewTaskDto> viewInProgressTask(TaskStatus status, User user) {
        user=(User)httpSession.getAttribute("user");

        List<Task> task = taskRepository.findByStatusAndUser(status, user);
        List<ViewTaskDto> listOfInProgress = new ArrayList<>();

        if(task.isEmpty()){
            throw new CustomAppException("No task to view");
        }

        for (int i = 0; i < task.size(); i++) {
            ViewTaskDto viewTaskDto = new ViewTaskDto();

            viewTaskDto.setTitle(task.get(i).getTitle());
            viewTaskDto.setDescription(task.get(i).getDescription());
            viewTaskDto.setStatus(task.get(i).getStatus());
            viewTaskDto.setCreatedAt(task.get(i).getCreatedAt());
            viewTaskDto.setCompletedAt(task.get(i).getCompletedAt());

            listOfInProgress.add(viewTaskDto);

        }

        return listOfInProgress;
    }

    @Override
    public void moveToPending(Long taskId, User user) {
        user=(User)httpSession.getAttribute("user");

        List<Task> pendingTask = taskRepository.findByIdAndUser(taskId, user);


        if(pendingTask.isEmpty()){
            throw new CustomAppException("No task to view");
        }

       Task task = pendingTask.get(0);

       task.setStatus(TaskStatus.PENDING);

       taskRepository.save(task);
    }

    @Override
    public void moveToDone(Long taskId, User user) {
        user=(User)httpSession.getAttribute("user");

        List<Task> completedTask = taskRepository.findByIdAndUser(taskId, user);

        if(completedTask.isEmpty()){
            throw new CustomAppException("No task to view");
        }
        Task task = completedTask.get(0);

        task.setCompletedAt(LocalDate.now());
        task.setStatus(TaskStatus.DONE);

        taskRepository.save(task);
    }

    @Override
    public String editTask(Long taskId,TaskDto taskDto, User user) {
        user=(User)httpSession.getAttribute("user");

        List<Task> updateTask = taskRepository.findByIdAndUser(taskId, user);

        if(updateTask.isEmpty()){
            throw new CustomAppException("No task to view");
        }
        Task task = new Task();
        task = updateTask.get(0);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setUpdatedAt(LocalDate.now());

        taskRepository.save(task);

        return "Success";
    }

    @Override
    public void deleteTask(Long taskId) {
        boolean exist = taskRepository.existsById(taskId);

        if(!exist){
            throw new IllegalStateException(
                    "Task with ID " + taskId + " does not exist"
            );
        }
        taskRepository.deleteById(taskId);
    }

    }

