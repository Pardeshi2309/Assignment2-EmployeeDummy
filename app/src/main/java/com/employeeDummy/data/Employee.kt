package com.employeeDummy.data



import java.io.Serializable

data class Employee(
    val name: String,
    val age: Int,
    val salary: Int
) : Serializable
