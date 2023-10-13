package com.example.todolist.task;

import com.example.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var user_id = request.getAttribute("user_id");
        taskModel.setUserId((UUID) user_id);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Data anterior a atual");
        }
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var user_id = request.getAttribute("user_id");
        var tasks = this.taskRepository.findByUserId((UUID) user_id);
        return tasks;
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity update(@RequestBody TaskModel taskModel,
                                 HttpServletRequest request,
                                 @PathVariable UUID id) {
        var userId = request.getAttribute("user_id");
        var task = this.taskRepository.findById(id).orElse(null);

        if (task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não encontrada");
        }
        if (!task.getUserId().equals(userId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User sem permissão");
        }
        Utils.copyNonNullProperties(taskModel, task);

        var taskUpdated = this.taskRepository.save(taskModel);
        return ResponseEntity.ok().body(taskUpdated);
    }

}
