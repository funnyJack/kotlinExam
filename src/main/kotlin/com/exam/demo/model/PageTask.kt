package com.exam.demo.model

data class PageTask(
    var currentPage:Int = 0 ,
    var pageSize:Int  = 5
) {
    var task :Task?=null
}