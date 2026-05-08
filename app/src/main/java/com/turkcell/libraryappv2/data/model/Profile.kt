package com.turkcell.libraryappv2.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable //dbden gelen nesneyi kotlin nesnesine dönüştürme işlemine serialization denir
data class Profile(
    @SerialName("user_id") val userId: String, // sql ile bizim verdiğimiz değer uyuşmazsa serall kullan
    val role: String,
    @SerialName("full_name")val fullName: String,
    @SerialName("student_no")val studentNo: String? = null
)