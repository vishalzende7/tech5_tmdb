package com.vishal.domain.people.model

data class Person(
    val id: Int,
    val name: String,
    val profileUrl: String?,
    val popularity: Double,
    val knownForDepartment: String
)
