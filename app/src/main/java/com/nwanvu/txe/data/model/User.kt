package com.nwanvu.txe.data.model

/**
 * Data class representing a GitHub user.
 *
 * @property id Unique identifier of the user.
 * @property name Full name of the user.
 * @property username GitHub username (login).
 * @property avatar URL of the user's avatar image.
 * @property profileUrl Optional URL to the user's GitHub profile.
 * @property location Geographical location of the user.
 * @property bio Short biography or description provided by the user.
 * @property blog URL to the user's blog or website.
 * @property followers Number of followers the user has.
 * @property following Number of users the user is following.
 */
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
