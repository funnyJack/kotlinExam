package com.exam.demo.controller

import com.exam.demo.model.PageTask
import com.exam.demo.model.Task
import com.exam.demo.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class TaskController(@Autowired var taskService: TaskService) {
    @PostMapping("addTask")
    fun addTask(@RequestBody task: Task) = taskService.addTask(task)
    @GetMapping("getAll")
    fun getAll() =  taskService.getAll()
    @GetMapping("getByID")
    fun getTaskById(id:Int) = taskService.getTaskById(id)
    @GetMapping("getByStatus")
    fun getTaskByStatus(status:Boolean) = taskService.getTaskByStatus(status)
    @GetMapping("getByPagination")
    fun getTskByPage(@RequestBody pageTask: PageTask)=taskService.getTaskByPagination(pageTask)
    @PutMapping("updateById")
    fun updateTaskById(@RequestBody task: Task) =  taskService.updateTaskById(task)
    @DeleteMapping("deleteById")
    fun deleteTaskById(id: Int) = taskService.deleteTaskById(id)

}