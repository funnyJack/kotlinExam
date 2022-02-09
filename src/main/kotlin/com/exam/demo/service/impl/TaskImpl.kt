package com.exam.demo.service.impl

import com.exam.demo.model.PageTask
import com.exam.demo.model.Task
import com.exam.demo.repository.TaskRepository
import com.exam.demo.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskImpl(@Autowired var repository : TaskRepository) :TaskService {
    override fun addTask(task:Task) = repository.save(task)
    override fun getAll() = repository.findAll()
    override fun getTaskById(id:Int) =repository.findById(id)
    override fun getTaskByStatus(status: Boolean) = repository.findAllByStatus(status)
    override fun getTaskByPagination(pageTask: PageTask) = repository.findAll(PageRequest.of(pageTask.currentPage,pageTask.pageSize))
    override fun updateTaskById(task: Task) = repository.save(task)
    override fun deleteTaskById(id: Int)  = repository.deleteById(id)
}