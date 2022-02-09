package com.exam.demo.model

import javax.persistence.*


@Entity
@Table(name = "task")

data class Task(
    @Column(name = "description")
    var description:String?,
    @Column(name = "status")
    var status:Boolean?,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?=null
)
