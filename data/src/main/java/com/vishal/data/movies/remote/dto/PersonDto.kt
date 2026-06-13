package com.vishal.data.movies.remote.dto

import com.google.gson.annotations.SerializedName

data class PersonDto(
    val id: Int,
    val name: String,
    @SerializedName("profile_path") val profilePath: String?,
    val popularity: Double,
    @SerializedName("known_for_department") val knownForDepartment: String
)
