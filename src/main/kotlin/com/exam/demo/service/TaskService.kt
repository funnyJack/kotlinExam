package com.exam.demo.service

import com.exam.demo.model.PageTask
import com.exam.demo.model.Task
import org.springframework.data.domain.Page
import java.util.*

interface TaskService {
   fun addTask(task: Task):Task
   fun getAll(): MutableIterable<Task>
   fun getTaskById(id:Int): Optional<Task>
   fun getTaskByStatus(status:Boolean):List<Task>
   fun getTaskByPagination(pageTask: PageTask): Page<Task>
   fun updateTaskById(task: Task) :Task
   fun deleteTaskById(id: Int)
}