package com.exam.demo.repository

import com.exam.demo.model.Task
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : PagingAndSortingRepository<Task,Int>{
    fun findAllByStatus(status:Boolean):List<Task>
}