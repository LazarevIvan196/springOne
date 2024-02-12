package org.example.controllers;

import org.example.domain.Task;
import org.example.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String tasksList(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> allTask = taskService.getAllTask((page - 1) * limit, limit);
        model.addAttribute("allTasks", allTask);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCountTask() / limit);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }

        return "tasks";
    }

    @GetMapping("/edit-task/{id}")
    public String editTask(@PathVariable(value = "id") Integer id, Model model) {
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }
        Task task = taskService.getById(id);
        model.addAttribute("task", task);

        return "editTasks";
    }


    @GetMapping("/add-task")
    public String addTask(Model model) {
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        taskService.createTask(task);
        return "redirect:/";
    }


    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable(value = "id") Integer id,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                             RedirectAttributes redirectAttributes) {
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }
        taskService.deleteTask(id);

        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("limit", limit);

        return "redirect:/";
    }

}
