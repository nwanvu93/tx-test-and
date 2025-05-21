package com.nwanvu.txe.data.model

data class User(
    val id: Int?,
    val name: String?,
    val username: String?,
    val avatar: String?,
    val profileUrl: String? = null,
    val location: String?,
    val bio: String?,
    val blog: String?,
    val followers: Int?,
    val following: Int?,
)
