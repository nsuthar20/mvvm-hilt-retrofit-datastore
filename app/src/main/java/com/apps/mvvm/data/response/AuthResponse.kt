package com.apps.mvvm.data.response

import java.io.Serializable

data class AuthResponse(
    val status: Int,
    val data: Data
) : Serializable {
    data class Data(
        val name: String,
        val auth_token: String
    )
}
