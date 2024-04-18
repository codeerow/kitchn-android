package com.spirit.kitchn.core.user.personal_info.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonalInfo(
    val id: String,
    val email: String,
    val username: String,
    val password: String,
)