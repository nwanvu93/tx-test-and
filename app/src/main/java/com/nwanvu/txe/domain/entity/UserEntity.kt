package com.nwanvu.txe.domain.entity

import com.google.gson.annotations.SerializedName
import com.nwanvu.txe.data.model.User

data class UserEntity(
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("login") val username: String? = null,
    @SerializedName("avatar_url") val avatar: String? = null,
    @SerializedName("html_url") val profileUrl: String? = null,
    val location: String? = null,
    val bio: String? = null,
    val blog: String? = null,
    val followers: Int? = null,
    val following: Int? = null,
) {

    companion object {
        fun empty() = UserEntity()
    }

    fun toData() = User(
        id = id,
        name = name,
        username = username,
        avatar = avatar,
        profileUrl = profileUrl,
        location = location,
        bio = bio,
        blog = blog,
        followers = followers,
        following = following
    )
}
