package com.spirit.kitchn.core.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserState(
    val id: String,
    val email: String,
    val username: String,
    val password: String,
)