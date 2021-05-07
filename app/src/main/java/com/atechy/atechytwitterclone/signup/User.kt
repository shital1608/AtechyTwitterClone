package com.atechy.atechytwitterclone.signup

/**
 * This model is ued to get authenticated user data
 */
data class User(
    var uid: String? = "",
    val name: String? = "",
    val email: String? = ""
)