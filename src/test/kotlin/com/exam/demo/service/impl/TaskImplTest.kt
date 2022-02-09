package com.exam.demo.service.impl

import com.exam.demo.model.Task
import com.exam.demo.repository.TaskRepository
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.assertj.core.api.Assertions.assertThat
import org.springframework.data.domain.PageRequest

@DataJpaTest //默认情况下，用@DataJpaTest 注解的测试使用嵌入式内存数据库。
internal class TaskImplTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val taskRepository: TaskRepository
) {
    @Test
    fun addTask() {
    val task = Task("dataJpaTest",true,2)
        taskRepository.save(task)
       val found =  entityManager.find(task::class.java,2)
        entityManager.flush()  //同步持久上下文环境，即将持久上下文环境的所有未保存实体的状态信息保存到数据库中。
        assertThat(found).isEqualTo(task)
    }

    @Test
    fun getAll() {
        val task = Task("dataJpaTest getAll",true)
        val taskList = listOf(task)
        entityManager.persist(task)
        entityManager.flush()
        val found = taskRepository.findAll()
        assertThat(found).isEqualTo(taskList)

    }

    @Test
    fun getTaskById() {
    val task = Task("dataJpaTest getById",true)
        val id = entityManager.persistAndGetId(task).toString().toInt()
        entityManager.flush()
        val found = taskRepository.findById(id)
        assertThat(found.get()).isEqualTo(task)
    }

    @Test
    fun getTaskByStatus() {
        val task = Task("dataJpaTest getByStatus",true)
        val id = entityManager.persistAndGetId(task).toString().toInt()
        entityManager.flush()
        val found = taskRepository.findById(id)
        assertThat(found.get().status).isEqualTo(task.status)
    }

    @Test
    fun getTaskByPagination() {
        val task1 = Task("task1",true)
        val task2 = Task("task2",true)
        entityManager.persist(task1)
        entityManager.persist(task2)
        entityManager.flush()
        val found = taskRepository.findAll(PageRequest.of(0,2))
        assertThat(found.get().count()).isEqualTo(2L)
    }

    @Test
    fun updateTaskById() {
        val task1 = Task("task1",true)
        val id = entityManager.persistAndGetId(task1).toString().toInt()
        entityManager.flush()
        val task2 = Task("task2",true,id)
        val save = taskRepository.save(task2)
        assertThat(save).isEqualTo(task2)

    }

    @Test
    fun deleteTaskById() {
        val task1 = Task("task1",true)
        val id = entityManager.persistAndGetId(task1).toString().toInt()
        entityManager.flush()
        taskRepository.deleteById(id)
        var found = entityManager.find(Task::class.java,id)
        assertThat(found).isEqualTo(null)
    }
}