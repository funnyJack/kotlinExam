package com.exam.demo

import com.exam.demo.model.Task
import com.exam.demo.service.TaskService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class DemoApplicationTests(@Autowired var service: TaskService) {
    @Test
    fun contextLoads() {
    }
}
