package com.exam.demo.controller

import com.alibaba.fastjson.JSON
import com.exam.demo.model.PageTask
import com.exam.demo.model.Task
import com.exam.demo.repository.TaskRepository
import com.exam.demo.service.TaskService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest
@AutoConfigureMockMvc //自动配置mockMvc
internal class TaskControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    val taskRepository: TaskRepository
) {
//    mock请求和返回的模板
    fun mockTemp(
        url: String,
        restType: String,
        submitType: String,
        task: Task?,
        currentPage: Int?,
        pageSize: Int?
    ) :RequestBuilder {
        /*
            mockMvc.perform(
                get("/getByID")   //发送的get请求,可以换成post，put，delete方法执行相应请求
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) //模拟表单提交
                    .param("id",task.id?.toString())   //get请求时填写参数的位置
                    .param("description",task.description)
            )
                .andExpect(status().isOk) //期望返回的状态码
                 .andExpect(jsonPath("\$.length()").value(3)) //期望返回的json字符串内容
            .andDo(MockMvcResultHandlers.print()) //打印返回的内容
                .andReturn().response.contentAsString //对返回字符串的json内容进行判断
    */
        var request: RequestBuilder
        when (restType) {
            "post" -> request = post(url)
            "get" -> request = get(url)
            "put" -> request = put(url)
            "delete" -> request = delete(url)
            else -> throw Throwable("传入的rest类型有错")
        }
        if (submitType == "json") {
//            分页相关操作
            val jsonStr =
                if (url == "/getByPagination") {
                    val pageTask =
                        if (currentPage != null && pageSize != null) {
                            PageTask(currentPage, pageSize)
                        } else {
                            PageTask()
                        }
                    pageTask.task = task
                    JSON.toJSONString(pageTask)
                } else {
                    JSON.toJSONString(task) //用fastjson转为json字符串
                }

            request = request.accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON) //模拟json格式传输
                .content(jsonStr)
        } else if (submitType == "form") {
            request = request.contentType(MediaType.APPLICATION_FORM_URLENCODED) //模拟表单提交
            if (task?.id != null) {
                request = request.param("id", task.id.toString())
            }
            if (task?.description != null) {
                request = request.param("description", task.description)
            }
            if (task?.status != null) {
                request = request.param("status", task.status.toString())
            }
        } else {
            throw Throwable("你输入的提交类型有误")
        }
        return request
    }

    @Test
//    模拟json传输测试
    fun testSave() {
        val task = Task("test mockMvc unit test", true)
        val request = mockTemp("/addTask", "post", "json", task, null, null)
        mockMvc.perform(request).andExpect(status().isOk)//期望返回的状态码
            .andDo(MockMvcResultHandlers.print())//打印返回的内容
            .andExpect(jsonPath("$.description").value(task.description))
            .andExpect(jsonPath("$.status").value(task.status))
    }

    @Test
    fun testFindAll() {
        val taskList = taskRepository.findAll()
        val request = mockTemp("/getAll", "get", "json", null, null, null)
        mockMvc.perform(request).andExpect(status().isOk)//期望返回的状态码
            .andDo(MockMvcResultHandlers.print())//打印返回的内容
            .andExpect(jsonPath("$.length()").value(taskList.toList().size))
    }


    @Test
//    模拟表单提交测试
    fun testFindById() {
        val task =Task(null, null, 1)
           val request = mockTemp("/getByID", "get", "form", task, null, null)
            mockMvc.perform(request).andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(task.id))
    }

    @Test
    fun testFindByStatus() {
        val task = Task(null,false,null)
        val request = mockTemp("/getByStatus", "get", "json", task, null, null)
        mockMvc.perform(request).andExpect(status().isOk)
            .andExpect(jsonPath("$.[0].status").value(task.status))
    }

    @Test
    fun testFindByPage() {
        val taskList = taskRepository.findAll(PageRequest.of(0,2))
        val request = mockTemp("/getByPagination", "get", "json", null, 0, 2)
        mockMvc.perform(request).andExpect(status().isOk)//期望返回的状态码
            .andDo(MockMvcResultHandlers.print())//打印返回的内容
            .andExpect(jsonPath("$.size").value(taskList.toList().size))

    }

    @Test
    fun testUpdateById() {
        val task =  Task("mockMvc update test", false, 8)
       val request = mockTemp("/updateById", "put", "json",task, null, null)
            mockMvc.perform(request).andExpect(status().isOk)//期望返回的状态码
            .andDo(MockMvcResultHandlers.print())//打印返回的内容
            .andExpect(jsonPath("$.description").value(task.description))
            .andExpect(jsonPath("$.status").value(task.status))
            .andExpect(jsonPath("$.id").value(task.id))

    }
    @Test
    fun testDeleteById() {
        val task =  Task(null, null, 24)
    val request = mockTemp("/deleteById", "delete", "form",task, null, null)
        mockMvc.perform(request).andExpect(status().isOk)//期望返回的状态码
            .andDo(MockMvcResultHandlers.print())//打印返回的内容
    }

}